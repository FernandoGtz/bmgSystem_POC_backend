package com.BMG_System_POC.demo.service;

import com.BMG_System_POC.demo.dto.AuthRequestDTO;
import com.BMG_System_POC.demo.dto.AuthResponseDTO;
import com.BMG_System_POC.demo.dto.TokenRefreshRequestDTO;
import com.BMG_System_POC.demo.entity.RefreshToken;
import com.BMG_System_POC.demo.entity.User;
import com.BMG_System_POC.demo.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final RefreshTokenService refreshTokenService;

    public AuthService(AuthenticationManager authenticationManager,
                       JwtService jwtService,
                       UserDetailsService userDetailsService, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.refreshTokenService = refreshTokenService;
    }

    public AuthResponseDTO login(AuthRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Cargamos el usuario en base al email
        UserDetails user = userDetailsService.loadUserByUsername(request.getEmail());

        // Generamos token de acceso y token de refresco
        String refreshToken = refreshTokenService.createRefreshToken(request.getEmail()).getToken();
        String accessToken = jwtService.generateToken(new java.util.HashMap<>(), user);

        return new AuthResponseDTO(accessToken, refreshToken);
    }

    @Transactional
    public AuthResponseDTO refreshToken(TokenRefreshRequestDTO request) {
        // Obtener el token del DTO
        String refreshTokenString = request.getRefreshToken();

        // Buscar el token en la base de datos
        RefreshToken refreshToken = refreshTokenService.findByToken(refreshTokenString)
                .orElseThrow(() -> new RuntimeException("Refresh token no encontrado. Inicia sesión nuevamente."));

        // Validar que no esté expirado
        refreshTokenService.verifyExpiration(refreshToken);

        // Extraer el usuario asociado al token
        User user = refreshToken.getUser();

        // Convertir User a UserDetails para poder generar el token JWT
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

        // Generar nuevo Access Token con duración de 5 minutos
        String newAccessToken = jwtService.generateToken(new java.util.HashMap<>(), userDetails);

        // Retornar el nuevo Access Token junto con el mismo Refresh Token
        return new AuthResponseDTO(newAccessToken, refreshTokenString);
    }
}
