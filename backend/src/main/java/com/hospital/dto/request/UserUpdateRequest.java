package com.hospital.dto.request;


import lombok.Data;

@Data
public class UserUpdateRequest {

    private String username;

    private String email;

    private String phone;

}