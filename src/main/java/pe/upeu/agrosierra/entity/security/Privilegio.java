package pe.upeu.agrosierra.entity.security;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "PRIVILEGIOS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Privilegio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    @Column(name = "NOMBRE", nullable = false, unique = true, length = 50)
    private String nombre;

    @Size(max = 150)
    @Column(name = "DESCRIPCION", length = 150)
    private String descripcion;
}