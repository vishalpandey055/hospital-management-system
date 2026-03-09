package com.hospital.dto;

public class DoctorResponse {

    private Long id;
    private String name;
    private String specialization;
    private String availableDays;
    private String departmentName;

    public DoctorResponse() {}

    public DoctorResponse(Long id, String name,
                          String specialization,
                          String availableDays,
                          String departmentName) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.availableDays = availableDays;
        this.departmentName = departmentName;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

    // Getters & Setters
}