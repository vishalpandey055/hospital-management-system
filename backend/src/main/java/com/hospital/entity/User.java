package com.hospital.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	private String email;

	private String phone;

	@Enumerated(EnumType.STRING)
	private Role role;

	@OneToOne(mappedBy = "user")
	@JsonIgnore
	private Doctor doctor;

	public User() {
	}

	public User(Long id, String username, String password, String email, String phone, Role role) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.role = role;
	}

	// ======================
	// GETTERS
	// ======================

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public Role getRole() {
		return role;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	// ======================
	// SETTERS
	// ======================

	public void setId(Long id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
}