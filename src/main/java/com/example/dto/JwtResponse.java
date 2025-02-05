package com.example.dto;

import com.example.entity.Admin;

public record JwtResponse(String accessToken, String refreshToken, Admin admin) {
    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Admin getAdmin() {
        return admin;
    }
}