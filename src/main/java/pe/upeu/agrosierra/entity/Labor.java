package pe.upeu.agrosierra.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import pe.upeu.agrosierra.enums.TipoLabor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "LABORES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Labor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "LOTE_ID", nullable = false)
    private Lote lote;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO", nullable = false, length = 20)
    private TipoLabor tipo;

    @NotBlank
    @Size(max = 10)
    @Column(name = "FECHA", nullable = false, length = 10)
    private String fecha;

    @NotBlank
    @Size(max = 50)
    @Column(name = "RESPONSABLE", nullable = false, length = 50)
    private String responsable;

    @NotNull
    @DecimalMin(value = "0.01")
    @Column(name = "CANTIDAD", nullable = false, precision = 10, scale = 2)
    private BigDecimal cantidad;

    @Size(max = 255)
    @Column(name = "OBSERVACION", length = 255)
    private String observacion;

    @Column(name = "FECHA_REGISTRO", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    @PrePersist
    public void prePersist() {
        if (this.fechaRegistro == null) {
            this.fechaRegistro = LocalDateTime.now();
        }
    }
}