package com.BMG_System_POC.demo.service;

import com.BMG_System_POC.demo.entity.RefreshToken;
import com.BMG_System_POC.demo.entity.User;
import com.BMG_System_POC.demo.repository.RefreshTokenRepository;
import com.BMG_System_POC.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Value("${api.security.refresh-token.expiration}")
    private long refreshTokenExpiration;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    public RefreshToken createRefreshToken(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));
        // Crear objeto
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        // Generar token con UUID
        refreshToken.setToken(UUID.randomUUID().toString());
        // Establecer expiración (ejemplo: 24 horas)
        refreshToken.setExpires(Instant.now().plusMillis(refreshTokenExpiration));
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpires().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token expirado. Inicia sesión nuevamente.");
        }
        return token;
    }

    @Transactional
    public void deletedByUserEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Usuario no encontrado")) ;
        refreshTokenRepository.deleteByUser(user);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }
}
