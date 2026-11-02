package pe.upeu.agrosierra.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.upeu.agrosierra.dto.LaborRequestDTO;
import pe.upeu.agrosierra.dto.LaborResponseDTO;
import pe.upeu.agrosierra.dto.PaginaResponseDTO;
import pe.upeu.agrosierra.entity.Labor;
import pe.upeu.agrosierra.entity.Lote;
import pe.upeu.agrosierra.enums.TipoLabor;
import pe.upeu.agrosierra.exception.RecursoNoEncontradoException;
import pe.upeu.agrosierra.exception.ReglaNegocioException;
import pe.upeu.agrosierra.exception.ValidacionException;
import pe.upeu.agrosierra.mapper.LaborMapper;
import pe.upeu.agrosierra.repository.LaborRepository;
import pe.upeu.agrosierra.repository.LoteRepository;
import pe.upeu.agrosierra.service.services.LaborService;
import pe.upeu.agrosierra.util.PaginacionUtil;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

@Service
public class LaborServiceImpl implements LaborService {

    private static final Logger log = LoggerFactory.getLogger(LaborServiceImpl.class);

    private static final Set<String> CAMPOS_ORDENABLES = Set.of("id", "fecha", "cantidad", "tipo");
    private static final String ORDEN_POR_DEFECTO = "fecha";

    private final LaborRepository laborRepository;
    private final LoteRepository loteRepository;
    private final LaborMapper laborMapper;

    public LaborServiceImpl(LaborRepository laborRepository,
                            LoteRepository loteRepository,
                            LaborMapper laborMapper) {
        this.laborRepository = laborRepository;
        this.loteRepository = loteRepository;
        this.laborMapper = laborMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public PaginaResponseDTO<LaborResponseDTO> listarPorLote(Long loteId, int pagina, int tamanio, String ordenarPor, String direccion) {
        if (!loteRepository.existsById(loteId)) {
            throw new RecursoNoEncontradoException("Lote no encontrado con id: " + loteId);
        }

        Pageable pageable = PaginacionUtil.construir(pagina, tamanio, ordenarPor, direccion, CAMPOS_ORDENABLES, ORDEN_POR_DEFECTO);
        Page<LaborResponseDTO> resultado = laborRepository.findByLoteIdOrderByFechaDesc(loteId, pageable)
                .map(laborMapper::toResponse);

        return PaginaResponseDTO.de(resultado);
    }

    @Override
    @Transactional(readOnly = true)
    public LaborResponseDTO buscar(Long id) {
        Labor labor = laborRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Labor no encontrada con id: " + id));
        return laborMapper.toResponse(labor);
    }

    @Override
    @Transactional
    public LaborResponseDTO registrar(LaborRequestDTO request, String usernameAutenticado) {
        log.info("Registrando labor para lote id={} por usuario={}", request.getLoteId(), usernameAutenticado);

        Lote lote = loteRepository.findById(request.getLoteId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Lote no encontrado con id: " + request.getLoteId()));

        if (Boolean.TRUE.equals(lote.getCampaniaCerrada())) {
            throw new ReglaNegocioException("No se puede registrar labores en un lote con campaña cerrada");
        }

        validarDosisFumigacion(request, lote);

        Labor labor = Labor.builder()
                .lote(lote)
                .tipo(request.getTipo())
                .fecha(request.getFecha())
                .responsable(usernameAutenticado != null ? usernameAutenticado : request.getResponsable().trim())
                .cantidad(request.getCantidad())
                .observacion(request.getObservacion() != null ? request.getObservacion().trim() : null)
                .build();

        Labor guardada = laborRepository.save(labor);
        log.info("Labor registrada exitosamente id={}", guardada.getId());
        return laborMapper.toResponse(guardada);
    }

    @Override
    @Transactional
    public LaborResponseDTO corregir(Long id, LaborRequestDTO request, String usernameAutenticado) {
        log.info("Corrigiendo labor id={} por usuario={}", id, usernameAutenticado);

        Labor labor = laborRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Labor no encontrada con id: " + id));

        Lote lote = labor.getLote();

        if (Boolean.TRUE.equals(lote.getCampaniaCerrada())) {
            throw new ReglaNegocioException("No se puede corregir una labor de un lote con campaña cerrada");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean esJefe = auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_JEFE_CAMPO") || a.getAuthority().equals("CAMPANIA_CERRAR"));

        if (!esJefe) {
            if (!labor.getResponsable().equalsIgnoreCase(usernameAutenticado)) {
                throw new AccessDeniedException("Un técnico solo puede corregir las labores que él mismo registró");
            }

            long horasTranscurridas = Duration.between(labor.getFechaRegistro(), LocalDateTime.now()).toHours();
            if (horasTranscurridas >= 24) {
                throw new AccessDeniedException("El plazo de 24 horas para corregir esta labor ha expirado");
            }
        }

        validarDosisFumigacion(request, lote);

        labor.setTipo(request.getTipo());
        labor.setFecha(request.getFecha());
        labor.setCantidad(request.getCantidad());
        labor.setObservacion(request.getObservacion() != null ? request.getObservacion().trim() : null);

        Labor actualizada = laborRepository.save(labor);
        return laborMapper.toResponse(actualizada);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Labor labor = laborRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Labor no encontrada con id: " + id));

        if (Boolean.TRUE.equals(labor.getLote().getCampaniaCerrada())) {
            throw new ReglaNegocioException("No se puede eliminar labores de un lote con campaña cerrada");
        }

        laborRepository.delete(labor);
        log.info("Labor id={} eliminada", id);
    }

    private void validarDosisFumigacion(LaborRequestDTO request, Lote lote) {
        if (request.getTipo() == TipoLabor.FUMIGACION) {
            BigDecimal maximoPermitido = lote.getDosisMaximaPorHectarea().multiply(lote.getHectareas());
            if (request.getCantidad().compareTo(maximoPermitido) > 0) {
                throw new ValidacionException(
                        "cantidad",
                        "La cantidad (" + request.getCantidad() + ") supera la dosis máxima permitida para este lote (" + maximoPermitido + ")"
                );
            }
        }
    }
}