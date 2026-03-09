package com.hospital.dto;

public class AuthResponse {

    private String token;

    // No-Args Constructor
    public AuthResponse() {
    }

    // All-Args Constructor
    public AuthResponse(String token) {
        this.token = token;
    }

    // Getter
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}