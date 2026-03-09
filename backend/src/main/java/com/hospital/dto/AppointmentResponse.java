package com.hospital.dto;

import com.hospital.entity.AppointmentStatus;
import java.time.LocalDateTime;

public class AppointmentResponse {

	private Long id;
	private LocalDateTime appointmentDate;
	private AppointmentStatus status;
	private String doctorName;
	private String patientName;

	public AppointmentResponse(Long id, LocalDateTime appointmentDate, AppointmentStatus status, String doctorName,
			String patientName) {
		this.id = id;
		this.appointmentDate = appointmentDate;
		this.status = status;
		this.doctorName = doctorName;
		this.patientName = patientName;
	}

	public Long getId() {
		return id;
	}

	public LocalDateTime getAppointmentDate() {
		return appointmentDate;
	}

	public AppointmentStatus getStatus() {
		return status;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public String getPatientName() {
		return patientName;
	}
}