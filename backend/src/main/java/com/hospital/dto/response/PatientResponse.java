package com.hospital.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PatientResponse {

    private Long id;

    private String name;

    private int age;

    private String gender;

    private String phone;

    private String email;

}