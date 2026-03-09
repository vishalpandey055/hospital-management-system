package com.hospital.controller;

import com.hospital.dto.AuthRequest;
import com.hospital.dto.AuthResponse;
import com.hospital.dto.RegisterRequest;
import com.hospital.entity.Role;
import com.hospital.entity.User;
import com.hospital.entity.Patient;
import com.hospital.repository.UserRepository;
import com.hospital.repository.PatientRepository;
import com.hospital.security.JwtUtil;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository,
                          PatientRepository patientRepository,
                          BCryptPasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest request) {

        Optional<User> optionalUser =
                userRepository.findByUsername(request.getUsername());

        if (optionalUser.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }

        String token =
                jwtUtil.generateToken(user.getUsername(), user.getRole().name());

        return ResponseEntity.ok(
                new AuthResponse(token)
        );
    }

    // REGISTER
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role role;

        try {
            role = Role.valueOf(request.getRole().toUpperCase());
            user.setRole(role);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Invalid role. Allowed: ADMIN, DOCTOR, PATIENT");
        }

        User savedUser = userRepository.save(user);

        // Create patient record automatically
        if (role == Role.PATIENT) {

            Patient patient = new Patient();
            patient.setName(request.getUsername());
            patient.setUser(savedUser);

            patientRepository.save(patient);
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("User registered successfully");
    }
}