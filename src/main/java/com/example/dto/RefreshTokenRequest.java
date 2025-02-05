package com.example.dto;

public record RefreshTokenRequest(String refreshToken) {
    public String getRefreshToken() {
        return refreshToken;
    }
}
