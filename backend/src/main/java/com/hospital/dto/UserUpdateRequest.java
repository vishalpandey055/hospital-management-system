package com.hospital.dto;

public class UserUpdateRequest {

    private String username;
    private String email;
    private String phone;

    // No-Args Constructor
    public UserUpdateRequest() {
    }

    // All-Args Constructor
    public UserUpdateRequest(String username, String email, String phone) {
        this.username = username;
        this.email = email;
        this.phone = phone;
    }

    // Getters

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    // Setters

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}