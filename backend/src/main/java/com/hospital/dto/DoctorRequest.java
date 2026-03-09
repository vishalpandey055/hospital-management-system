package com.hospital.dto;

public class DoctorRequest {

    private String name;
    private String specialization;
    private String availableDays;
    private Long departmentId;

    // User details for login
    private String username;
    private String password;

    public DoctorRequest() {}

    public DoctorRequest(String name, String specialization,
                         String availableDays, Long departmentId,
                         String username, String password) {
        this.name = name;
        this.specialization = specialization;
        this.availableDays = availableDays;
        this.departmentId = departmentId;
        this.username = username;
        this.password = password;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String getAvailableDays() {
		return availableDays;
	}

	public void setAvailableDays(String availableDays) {
		this.availableDays = availableDays;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

   
}