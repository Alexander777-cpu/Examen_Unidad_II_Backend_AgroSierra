package pe.upeu.agrosierra.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoteRequestDTO {

    @NotBlank(message = "El código es obligatorio")
    @Size(max = 30, message = "El código no puede exceder 30 caracteres")
    private String codigo;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;

    @NotNull(message = "Las hectáreas son obligatorias")
    @DecimalMin(value = "0.01", message = "Las hectáreas deben ser mayores a cero")
    private BigDecimal hectareas;

    @NotBlank(message = "El cultivo es obligatorio")
    @Size(max = 80, message = "El cultivo no puede exceder 80 caracteres")
    private String cultivo;

    @NotBlank(message = "El valle es obligatorio")
    @Size(max = 80, message = "El valle no puede exceder 80 caracteres")
    private String valle;

    @NotNull(message = "La dosis máxima por hectárea es obligatoria")
    @DecimalMin(value = "0.01", message = "La dosis máxima debe ser mayor a cero")
    private BigDecimal dosisMaximaPorHectarea;
}