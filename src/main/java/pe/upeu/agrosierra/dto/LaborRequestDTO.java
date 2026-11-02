package pe.upeu.agrosierra.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.upeu.agrosierra.enums.TipoLabor;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LaborRequestDTO {

    @NotNull(message = "El id del lote es obligatorio")
    private Long loteId;

    @NotNull(message = "El tipo de labor es obligatorio (RIEGO, FUMIGACION, COSECHA)")
    private TipoLabor tipo;

    @NotBlank(message = "La fecha es obligatoria")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "La fecha debe tener formato YYYY-MM-DD")
    private String fecha;

    @NotBlank(message = "El responsable es obligatorio")
    @Size(max = 50, message = "El responsable no puede exceder 50 caracteres")
    private String responsable;

    @NotNull(message = "La cantidad es obligatoria")
    @DecimalMin(value = "0.01", message = "La cantidad debe ser mayor a cero")
    private BigDecimal cantidad;

    @Size(max = 255, message = "La observación no puede superar los 255 caracteres")
    private String observacion;
}