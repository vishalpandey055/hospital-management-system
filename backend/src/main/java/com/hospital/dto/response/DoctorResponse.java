package com.hospital.dto.response;


import lombok.Data;

@Data
public class DoctorResponse {

    private Long id;

    private String name;

    private String specialization;

    private String availableDays;

    private String departmentName;

}