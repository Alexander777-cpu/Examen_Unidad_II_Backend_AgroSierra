package pe.upeu.agrosierra.dto;

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
public class LoteResponseDTO {

    private Long id;
    private String codigo;
    private String nombre;
    private BigDecimal hectareas;
    private String cultivo;
    private String valle;
    private Boolean campaniaCerrada;
    private BigDecimal dosisMaximaPorHectarea;
}