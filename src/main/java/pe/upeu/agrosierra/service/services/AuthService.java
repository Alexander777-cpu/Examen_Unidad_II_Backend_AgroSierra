package pe.upeu.agrosierra.service.services;

import pe.upeu.agrosierra.dto.auth.LoginRequestDTO;
import pe.upeu.agrosierra.dto.auth.LoginResponseDTO;
import pe.upeu.agrosierra.dto.auth.UsuarioResponseDTO;

public interface AuthService {

    LoginResponseDTO login(LoginRequestDTO request);

    UsuarioResponseDTO usuarioActual(String username);
}