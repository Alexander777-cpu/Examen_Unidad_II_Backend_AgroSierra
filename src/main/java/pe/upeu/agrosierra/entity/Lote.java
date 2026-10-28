package pe.upeu.agrosierra.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "LOTES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 30)
    @Column(name = "CODIGO", nullable = false, unique = true, length = 30)
    private String codigo;

    @NotBlank
    @Size(max = 100)
    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;

    @NotNull
    @DecimalMin(value = "0.01")
    @Column(name = "HECTAREAS", nullable = false, precision = 8, scale = 2)
    private BigDecimal hectareas;

    @NotBlank
    @Size(max = 80)
    @Column(name = "CULTIVO", nullable = false, length = 80)
    private String cultivo;

    @NotBlank
    @Size(max = 80)
    @Column(name = "VALLE", nullable = false, length = 80)
    private String valle;

    @Column(name = "CAMPANIA_CERRADA", nullable = false)
    @Builder.Default
    private Boolean campaniaCerrada = false;

    @NotNull
    @Column(name = "DOSIS_MAXIMA_POR_HECTAREA", nullable = false, precision = 8, scale = 2)
    private BigDecimal dosisMaximaPorHectarea;

    @JsonIgnore
    @OneToMany(mappedBy = "lote", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Labor> labores = new ArrayList<>();
}