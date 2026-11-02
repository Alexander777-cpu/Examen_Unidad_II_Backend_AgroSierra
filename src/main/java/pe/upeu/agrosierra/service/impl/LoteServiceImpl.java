package pe.upeu.agrosierra.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.upeu.agrosierra.dto.LoteRequestDTO;
import pe.upeu.agrosierra.dto.LoteResponseDTO;
import pe.upeu.agrosierra.dto.PaginaResponseDTO;
import pe.upeu.agrosierra.entity.Lote;
import pe.upeu.agrosierra.enums.TipoLabor;
import pe.upeu.agrosierra.exception.RecursoNoEncontradoException;
import pe.upeu.agrosierra.exception.ReglaNegocioException;
import pe.upeu.agrosierra.mapper.LoteMapper;
import pe.upeu.agrosierra.repository.LaborRepository;
import pe.upeu.agrosierra.repository.LoteRepository;
import pe.upeu.agrosierra.service.services.LoteService;
import pe.upeu.agrosierra.util.PaginacionUtil;

import java.util.Set;

@Service
public class LoteServiceImpl implements LoteService {

    private static final Logger log = LoggerFactory.getLogger(LoteServiceImpl.class);

    private static final Set<String> CAMPOS_ORDENABLES = Set.of("id", "codigo", "nombre", "hectareas", "cultivo", "valle");
    private static final String ORDEN_POR_DEFECTO = "id";

    private final LoteRepository loteRepository;
    private final LaborRepository laborRepository;
    private final LoteMapper loteMapper;

    public LoteServiceImpl(LoteRepository loteRepository,
                           LaborRepository laborRepository,
                           LoteMapper loteMapper) {
        this.loteRepository = loteRepository;
        this.laborRepository = laborRepository;
        this.loteMapper = loteMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public PaginaResponseDTO<LoteResponseDTO> listar(String valle, int pagina, int tamanio, String ordenarPor, String direccion) {
        long inicio = System.currentTimeMillis();
        log.info("Inicio listar lotes | valle={} | pagina={} | tamanio={}", valle, pagina, tamanio);

        Pageable pageable = PaginacionUtil.construir(pagina, tamanio, ordenarPor, direccion, CAMPOS_ORDENABLES, ORDEN_POR_DEFECTO);

        String valleFiltro = (valle != null && !valle.isBlank()) ? valle.trim() : null;
        Page<LoteResponseDTO> resultado = loteRepository.buscar(valleFiltro, pageable)
                .map(loteMapper::toResponse);

        log.info("Fin listar lotes | filas={} | total={} | duracionMs={}",
                resultado.getNumberOfElements(), resultado.getTotalElements(), System.currentTimeMillis() - inicio);

        return PaginaResponseDTO.de(resultado);
    }

    @Override
    @Transactional(readOnly = true)
    public LoteResponseDTO buscar(Long id) {
        log.info("Buscando lote id={}", id);
        Lote lote = loteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Lote no encontrado con id: " + id));
        return loteMapper.toResponse(lote);
    }

    @Override
    @Transactional
    public LoteResponseDTO cerrarCampania(Long id) {
        log.info("Intentando cerrar campania para lote id={}", id);
        Lote lote = loteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Lote no encontrado con id: " + id));

        if (Boolean.TRUE.equals(lote.getCampaniaCerrada())) {
            throw new ReglaNegocioException("La campaña del lote ya se encuentra cerrada");
        }

        // RN-03: La campaña no puede cerrarse si el lote no tiene al menos una labor de tipo COSECHA
        boolean tieneCosecha = laborRepository.existsByLoteIdAndTipo(id, TipoLabor.COSECHA);
        if (!tieneCosecha) {
            throw new ReglaNegocioException("No se puede cerrar la campaña de un lote sin al menos una labor de tipo COSECHA registrada");
        }

        lote.setCampaniaCerrada(true);
        Lote actualizado = loteRepository.save(lote);
        log.info("Campaña cerrada correctamente para lote id={}", id);

        return loteMapper.toResponse(actualizado);
    }

    @Override
    @Transactional
    public LoteResponseDTO crear(LoteRequestDTO request) {
        String codigo = request.getCodigo().trim();
        if (loteRepository.existsByCodigoIgnoreCase(codigo)) {
            throw new ReglaNegocioException("Ya existe un lote con el código: " + codigo);
        }

        Lote lote = Lote.builder()
                .codigo(codigo)
                .nombre(request.getNombre().trim())
                .hectareas(request.getHectareas())
                .cultivo(request.getCultivo().trim())
                .valle(request.getValle().trim())
                .campaniaCerrada(false)
                .dosisMaximaPorHectarea(request.getDosisMaximaPorHectarea())
                .build();

        return loteMapper.toResponse(loteRepository.save(lote));
    }
}