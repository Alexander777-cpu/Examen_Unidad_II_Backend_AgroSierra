package pe.upeu.agrosierra.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioRequestDTO {

    @NotBlank(message = "El usuario es obligatorio")
    @Size(min = 4, max = 50, message = "El usuario debe tener entre 4 y 50 caracteres")
    @Pattern(
            regexp = "^[a-zA-Z0-9._-]+$",
            message = "El usuario solo admite letras, números, punto, guion y guion bajo"
    )
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 72, message = "La contraseña debe tener entre 8 y 72 caracteres")
    private String password;

    @Size(max = 120, message = "El nombre completo no puede exceder 120 caracteres")
    private String nombreCompleto;

    @NotEmpty(message = "Debe asignar al menos un rol")
    private Set<@NotNull(message = "El id de rol no puede ser nulo") Long> rolIds;
}