package com.hospital.dto.request;


import lombok.Data;

@Data
public class PatientRequest {

    private String name;

    private int age;

    private String gender;

    private String phone;

    private String email;

    private String username;

    private String password;

}