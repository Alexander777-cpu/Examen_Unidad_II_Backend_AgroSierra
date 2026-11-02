package pe.upeu.agrosierra.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDTO {

    private String token;
    @Builder.Default
    private String tipo = "Bearer";
    private long expiraEnSegundos;
    private String username;
    private String nombreCompleto;
    private List<String> roles;
    private List<String> privilegios;
}