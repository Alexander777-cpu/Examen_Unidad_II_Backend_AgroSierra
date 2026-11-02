package pe.upeu.agrosierra.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.upeu.agrosierra.dto.auth.LoginRequestDTO;
import pe.upeu.agrosierra.dto.auth.LoginResponseDTO;
import pe.upeu.agrosierra.dto.auth.UsuarioResponseDTO;
import pe.upeu.agrosierra.entity.security.Usuario;
import pe.upeu.agrosierra.exception.RecursoNoEncontradoException;
import pe.upeu.agrosierra.mapper.UsuarioMapper;
import pe.upeu.agrosierra.repository.UsuarioRepository;
import pe.upeu.agrosierra.security.JwtService;
import pe.upeu.agrosierra.service.services.AuthService;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           JwtService jwtService,
                           UsuarioRepository usuarioRepository,
                           UsuarioMapper usuarioMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public LoginResponseDTO login(LoginRequestDTO request) {

        // Limpiamos el username para evitar errores por espacios accidentales
        String usernameLimpio = request.getUsername() != null ? request.getUsername().trim() : "";
        log.info("Intento de login para usuario={}", usernameLimpio);

        // Spring Security maneja la comparación del password (con el hash) internamente
        Authentication autenticacion = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usernameLimpio, request.getPassword())
        );

        UserDetails principal = (UserDetails) autenticacion.getPrincipal();
        Usuario usuario = usuarioRepository.findByUsernameIgnoreCase(principal.getUsername())
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado: " + usernameLimpio));

        String token = jwtService.generarToken(principal);
        List<String> roles = usuarioMapper.nombresRoles(usuario.getRoles());
        List<String> privilegios = usuarioMapper.nombresPrivilegios(usuario.getRoles());

        log.info("Login exitoso: usuario={} roles={} privilegios={}", usernameLimpio, roles, privilegios);

        return LoginResponseDTO.builder()
                .token(token)
                .tipo("Bearer")
                .expiraEnSegundos(jwtService.getExpiracionSegundos())
                .username(usuario.getUsername())
                .nombreCompleto(usuario.getNombreCompleto())
                .roles(roles)
                .privilegios(privilegios)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO usuarioActual(String username) {
        Usuario usuario = usuarioRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado: " + username));
        return usuarioMapper.toUsuarioResponse(usuario);
    }
}