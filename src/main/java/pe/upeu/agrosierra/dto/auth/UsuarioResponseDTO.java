package pe.upeu.agrosierra.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioResponseDTO {

    private Long id;
    private String username;
    private String nombreCompleto;
    private Boolean estado;
    private LocalDateTime fechaCreacion;
    private List<String> roles;
    private List<String> privilegios;
}