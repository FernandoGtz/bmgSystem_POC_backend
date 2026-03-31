package com.BMG_System_POC.demo.dto;

public class AuthResponseDTO {
    private String accessToken;
    private String refreshToken;

    public AuthResponseDTO() {}

    public AuthResponseDTO(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getToken() {
        return accessToken;
    }
    public void setToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
