package com.hospital.dto.response;

import com.hospital.entity.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AppointmentResponse {

    private Long id;
    private LocalDateTime appointmentDate;
    private AppointmentStatus status;
    private String doctorName;
    private String patientName;
}