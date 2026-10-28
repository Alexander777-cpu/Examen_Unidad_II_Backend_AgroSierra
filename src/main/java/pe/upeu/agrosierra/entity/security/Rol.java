package pe.upeu.agrosierra.entity.security;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ROLES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rol {

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "ROL_PRIVILEGIOS",
            joinColumns = @JoinColumn(name = "ROL_ID"),
            inverseJoinColumns = @JoinColumn(name = "PRIVILEGIO_ID")
    )
    @Builder.Default
    private Set<Privilegio> privilegios = new HashSet<>();
}