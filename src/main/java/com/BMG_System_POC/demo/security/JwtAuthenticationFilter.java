package com.BMG_System_POC.demo.security;

import com.BMG_System_POC.demo.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;

    public JwtAuthenticationFilter(CustomUserDetailsService customUserDetailsService, JwtService jwtService) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        // Obtener el header de autorización de la request
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // Si el header no existe o no comienza con "Bearer ", continuar sin autenticación
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // El token JWT viene después de "Bearer ", por eso usamos substring(7)
        jwt = authHeader.substring(7);
        // Extraer el nombre de usuario (email) codificado en el token JWT
        username = jwtService.extractUsername(jwt);

        // Verificamos
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Cargar los detalles del usuario desde la base de datos usando el username
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            // Verificar que el token JWT sea válido para este usuario
            if (jwtService.isTokenValid(jwt, userDetails)) {
                // Crear un token de autenticación sin credenciales (solo username y authorities)
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // sin credenciales (contraseña)
                        userDetails.getAuthorities() // autoridades/roles del usuario
                );
                // Agregar detalles adicionales de la request al token de autenticación
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Establecer la autenticación en el contexto de seguridad de Spring
                // Esto permite que el usuario esté autenticado durante el resto de la request
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }
}
