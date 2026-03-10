package com.hospital.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardResponse {

    private long totalPatients;

    private long totalDoctors;

    private long totalAppointments;

    private long completedAppointments;

}