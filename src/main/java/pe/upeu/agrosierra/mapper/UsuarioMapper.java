package pe.upeu.agrosierra.mapper;

import org.springframework.stereotype.Component;
import pe.upeu.agrosierra.dto.auth.RolResponseDTO;
import pe.upeu.agrosierra.dto.auth.UsuarioResponseDTO;
import pe.upeu.agrosierra.entity.security.Privilegio;
import pe.upeu.agrosierra.entity.security.Rol;
import pe.upeu.agrosierra.entity.security.Usuario;

import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

@Component
public class UsuarioMapper {

    public UsuarioResponseDTO toUsuarioResponse(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .username(usuario.getUsername())
                .nombreCompleto(usuario.getNombreCompleto())
                .estado(usuario.getEstado())
                .fechaCreacion(usuario.getFechaCreacion())
                .roles(nombresRoles(usuario.getRoles()))
                .privilegios(nombresPrivilegios(usuario.getRoles()))
                .build();
    }

    public RolResponseDTO toRolResponse(Rol rol) {
        if (rol == null) {
            return null;
        }

        List<String> privilegios = rol.getPrivilegios() != null
                ? rol.getPrivilegios().stream()
                .map(Privilegio::getNombre)
                .sorted()
                .toList()
                : List.of();

        return RolResponseDTO.builder()
                .id(rol.getId())
                .nombre(rol.getNombre())
                .descripcion(rol.getDescripcion())
                .privilegios(privilegios)
                .build();
    }

    public List<String> nombresRoles(Collection<Rol> roles) {
        if (roles == null) {
            return List.of();
        }
        return roles.stream()
                .map(Rol::getNombre)
                .sorted()
                .toList();
    }

    public List<String> nombresPrivilegios(Collection<Rol> roles) {
        if (roles == null) {
            return List.of();
        }
        TreeSet<String> privilegios = new TreeSet<>();
        for (Rol rol : roles) {
            if (rol.getPrivilegios() != null) {
                for (Privilegio priv : rol.getPrivilegios()) {
                    privilegios.add(priv.getNombre());
                }
            }
        }
        return privilegios.stream().toList();
    }
}