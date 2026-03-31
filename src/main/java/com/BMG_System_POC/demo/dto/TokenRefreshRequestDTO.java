package com.BMG_System_POC.demo.dto;

public class TokenRefreshRequestDTO {
    private String refreshToken;

    public TokenRefreshRequestDTO() {}

    public TokenRefreshRequestDTO(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
