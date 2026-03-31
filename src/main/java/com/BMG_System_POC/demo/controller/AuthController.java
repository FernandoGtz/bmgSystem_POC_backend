package com.BMG_System_POC.demo.controller;

import com.BMG_System_POC.demo.dto.AuthRequestDTO;
import com.BMG_System_POC.demo.dto.AuthResponseDTO;
import com.BMG_System_POC.demo.dto.TokenRefreshRequestDTO;
import com.BMG_System_POC.demo.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request) {
        AuthResponseDTO response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public AuthResponseDTO refreshToken(@RequestBody TokenRefreshRequestDTO request) {
        AuthResponseDTO response = authService.refreshToken(request);
        return new AuthResponseDTO(response.getToken(), response.getRefreshToken());
    }
}
