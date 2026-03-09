package com.hospital.controller;

import com.hospital.dto.ChangePasswordRequest;
import com.hospital.dto.UserResponse;
import com.hospital.dto.UserUpdateRequest;
import com.hospital.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasAnyRole('ADMIN','DOCTOR','PATIENT')")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ==============================
    // GET CURRENT USER PROFILE
    // ==============================
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    // ==============================
    // UPDATE PROFILE
    // ==============================
    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateCurrentUser(
            @RequestBody UserUpdateRequest request) {

        return ResponseEntity.ok(userService.updateCurrentUser(request));
    }

    // ==============================
    // CHANGE PASSWORD
    // ==============================
    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request) {

        userService.changePassword(request);

        return ResponseEntity.ok("Password updated successfully");
    }
}