package pe.upeu.agrosierra.mapper;

import org.springframework.stereotype.Component;
import pe.upeu.agrosierra.dto.LaborResponseDTO;
import pe.upeu.agrosierra.entity.Labor;

@Component
public class LaborMapper {

    public LaborResponseDTO toResponse(Labor labor) {
        if (labor == null) {
            return null;
        }

        return LaborResponseDTO.builder()
                .id(labor.getId())
                .loteId(labor.getLote() != null ? labor.getLote().getId() : null)
                .loteCodigo(labor.getLote() != null ? labor.getLote().getCodigo() : null)
                .loteNombre(labor.getLote() != null ? labor.getLote().getNombre() : null)
                .tipo(labor.getTipo())
                .fecha(labor.getFecha())
                .responsable(labor.getResponsable())
                .cantidad(labor.getCantidad())
                .observacion(labor.getObservacion())
                .fechaRegistro(labor.getFechaRegistro())
                .build();
    }
}