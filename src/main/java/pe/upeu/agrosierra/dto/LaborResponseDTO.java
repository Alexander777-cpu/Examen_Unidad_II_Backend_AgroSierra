package pe.upeu.agrosierra.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.upeu.agrosierra.enums.TipoLabor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LaborResponseDTO {

    private Long id;
    private Long loteId;
    private String loteCodigo;
    private String loteNombre;
    private TipoLabor tipo;
    private String fecha;
    private String responsable;
    private BigDecimal cantidad;
    private String observacion;
    private LocalDateTime fechaRegistro;
}