package pe.upeu.agrosierra.mapper;

import org.springframework.stereotype.Component;
import pe.upeu.agrosierra.dto.LoteResponseDTO;
import pe.upeu.agrosierra.entity.Lote;

@Component
public class LoteMapper {

    public LoteResponseDTO toResponse(Lote lote) {
        if (lote == null) {
            return null;
        }

        return LoteResponseDTO.builder()
                .id(lote.getId())
                .codigo(lote.getCodigo())
                .nombre(lote.getNombre())
                .hectareas(lote.getHectareas())
                .cultivo(lote.getCultivo())
                .valle(lote.getValle())
                .campaniaCerrada(lote.getCampaniaCerrada())
                .dosisMaximaPorHectarea(lote.getDosisMaximaPorHectarea())
                .build();
    }
}