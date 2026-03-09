package com.hospital.service;

import com.hospital.dto.PatientRequest;
import com.hospital.dto.PatientResponse;
import com.hospital.entity.Patient;
import com.hospital.entity.Role;
import com.hospital.entity.User;
import com.hospital.exception.ResourceAlreadyExistsException;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.repository.PatientRepository;
import com.hospital.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public PatientServiceImpl(PatientRepository patientRepository,
                              UserRepository userRepository,
                              BCryptPasswordEncoder passwordEncoder) {
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<PatientResponse> getAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PatientResponse createPatient(PatientRequest request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new ResourceAlreadyExistsException("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.PATIENT);

        User savedUser = userRepository.save(user);

        Patient patient = new Patient();
        patient.setName(request.getName());
        patient.setAge(request.getAge());
        patient.setGender(request.getGender());
        patient.setPhone(request.getPhone());
        patient.setEmail(request.getEmail());
        patient.setUser(savedUser);

        Patient savedPatient = patientRepository.save(patient);

        return mapToResponse(savedPatient);
    }

    @Override
    public PatientResponse updatePatient(Long id, PatientRequest request) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        patient.setName(request.getName());
        patient.setAge(request.getAge());
        patient.setGender(request.getGender());
        patient.setPhone(request.getPhone());
        patient.setEmail(request.getEmail());

        Patient updatedPatient = patientRepository.save(patient);

        return mapToResponse(updatedPatient);
    }

    @Override
    public void deletePatient(Long id) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        patientRepository.delete(patient);
    }

    private PatientResponse mapToResponse(Patient patient) {
        return new PatientResponse(
                patient.getId(),
                patient.getName(),
                patient.getAge(),
                patient.getGender(),
                patient.getPhone(),
                patient.getEmail()
        );
    }
}