package pe.upeu.agrosierra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;
import pe.upeu.agrosierra.security.JwtAuthenticationFilter;
import pe.upeu.agrosierra.security.JwtService;
import pe.upeu.agrosierra.security.RestAccessDeniedHandler;
import pe.upeu.agrosierra.security.RestAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private static final String[] RUTAS_PUBLICAS = {
            "/api/v1/auth/login",
            "/api/health",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/error"
    };

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            JwtService jwtService,
            UserDetailsService userDetailsService,
            CorsConfigurationSource corsConfigurationSource,
            RestAuthenticationEntryPoint authenticationEntryPoint,
            RestAccessDeniedHandler accessDeniedHandler) throws Exception {

        JwtAuthenticationFilter jwtFilter =
                new JwtAuthenticationFilter(jwtService, userDetailsService);

        http
                .csrf(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .sessionManagement(sesion ->
                        sesion.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas
                        .requestMatchers(RUTAS_PUBLICAS).permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // 1. Labores de un lote específico: LABOR_LEER (debe ir antes que /lotes/**)
                        .requestMatchers(HttpMethod.GET, "/api/v1/lotes/*/labores").hasAuthority("LABOR_LEER")

                        // 2. Cierre de campaña: CAMPANIA_CERRAR
                        .requestMatchers(HttpMethod.POST, "/api/v1/lotes/*/cerrar-campania").hasAuthority("CAMPANIA_CERRAR")

                        // 3. Padrón y detalle de lotes: LOTE_LEER
                        .requestMatchers(HttpMethod.GET, "/api/v1/lotes/**").hasAuthority("LOTE_LEER")

                        // 4. Labores genéricas: lectura exige LABOR_LEER
                        .requestMatchers(HttpMethod.GET, "/api/v1/labores/**").hasAuthority("LABOR_LEER")

                        // 5. Creación de labor: LABOR_CREAR
                        .requestMatchers(HttpMethod.POST, "/api/v1/labores").hasAuthority("LABOR_CREAR")

                        // 6. Corrección de labor: LABOR_EDITAR
                        .requestMatchers(HttpMethod.PUT, "/api/v1/labores/**").hasAuthority("LABOR_EDITAR")

                        // 7. Eliminación de labor: LABOR_ELIMINAR
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/labores/**").hasAuthority("LABOR_ELIMINAR")

                        // 8. Datos del usuario logueado: exige usuario autenticado
                        .requestMatchers("/api/v1/usuarios/me").authenticated()

                        // Todo lo demás exige estar autenticado
                        .anyRequest().authenticated())
                .exceptionHandling(errores -> errores
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /*
     * BCrypt: hash lento y con "sal" aleatoria. La misma contraseña
     * produce un hash distinto cada vez y no se puede revertir.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * Spring arma el AuthenticationManager con el UserDetailsService
     * (UsuarioDetailsService) y el PasswordEncoder declarados.
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}