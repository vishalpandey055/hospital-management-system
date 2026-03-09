package com.hospital.service;

import com.hospital.dto.DoctorRequest;
import com.hospital.dto.DoctorResponse;
import com.hospital.entity.Department;
import com.hospital.entity.Doctor;
import com.hospital.entity.Role;
import com.hospital.entity.User;
import com.hospital.exception.ResourceAlreadyExistsException;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.repository.DepartmentRepository;
import com.hospital.repository.DoctorRepository;
import com.hospital.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DoctorServiceImpl(DoctorRepository doctorRepository,
                             DepartmentRepository departmentRepository,
                             UserRepository userRepository,
                             PasswordEncoder passwordEncoder) {
        this.doctorRepository = doctorRepository;
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // =========================
    // GET ALL DOCTORS
    // =========================
    @Override
    public List<DoctorResponse> getAllDoctors() {

        return doctorRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // =========================
    // GET DOCTOR BY ID
    // =========================
    @Override
    public DoctorResponse getDoctorById(Long id) {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        return mapToResponse(doctor);
    }

    // =========================
    // CREATE DOCTOR
    // =========================
    @Override
    public DoctorResponse createDoctor(DoctorRequest request) {

        Department department = departmentRepository
                .findById(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        // Check duplicate username
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new ResourceAlreadyExistsException("Username already exists");
        }

        // Create login user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.DOCTOR);

        User savedUser = userRepository.save(user);

        // Create doctor
        Doctor doctor = new Doctor();
        doctor.setName(request.getName());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setAvailableDays(request.getAvailableDays());
        doctor.setDepartment(department);
        doctor.setUser(savedUser);

        Doctor savedDoctor = doctorRepository.save(doctor);

        return mapToResponse(savedDoctor);
    }

    // =========================
    // UPDATE DOCTOR
    // =========================
    @Override
    public DoctorResponse updateDoctor(Long id, DoctorRequest request) {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        Department department = departmentRepository
                .findById(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        doctor.setName(request.getName());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setAvailableDays(request.getAvailableDays());
        doctor.setDepartment(department);

        Doctor updatedDoctor = doctorRepository.save(doctor);

        return mapToResponse(updatedDoctor);
    }

    // =========================
    // DELETE DOCTOR
    // =========================
    @Override
    public void deleteDoctor(Long id) {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        User user = doctor.getUser();

        doctorRepository.delete(doctor);

        if (user != null) {
            userRepository.delete(user);
        }
    }

    // =========================
    // ENTITY → DTO
    // =========================
    private DoctorResponse mapToResponse(Doctor doctor) {

        String departmentName = null;

        if (doctor.getDepartment() != null) {
            departmentName = doctor.getDepartment().getName();
        }

        DoctorResponse response = new DoctorResponse();
        response.setId(doctor.getId());
        response.setName(doctor.getName());
        response.setSpecialization(doctor.getSpecialization());
        response.setAvailableDays(doctor.getAvailableDays());
        response.setDepartmentName(departmentName);

        return response;
    }
}