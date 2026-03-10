package com.hospital.controller;

import com.hospital.dto.request.DepartmentRequest;
import com.hospital.dto.response.DepartmentResponse;
import com.hospital.service.DepartmentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class DepartmentController {

    private final DepartmentService departmentService;

    // CREATE DEPARTMENT
    @PostMapping
    public DepartmentResponse createDepartment(
            @Valid @RequestBody DepartmentRequest request) {

        return departmentService.createDepartment(request);
    }

    // GET ALL DEPARTMENTS (Pagination)
    @GetMapping
    public Page<DepartmentResponse> getAllDepartments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        return departmentService.getAllDepartments(page, size, sortBy);
    }

    // GET DEPARTMENT BY ID
    @GetMapping("/{id}")
    public DepartmentResponse getDepartmentById(@PathVariable Long id) {

        return departmentService.getDepartmentById(id);
    }

    // UPDATE DEPARTMENT
    @PutMapping("/{id}")
    public DepartmentResponse updateDepartment(
            @PathVariable Long id,
            @Valid @RequestBody DepartmentRequest request) {

        return departmentService.updateDepartment(id, request);
    }

    // DELETE DEPARTMENT
    @DeleteMapping("/{id}")
    public String deleteDepartment(@PathVariable Long id) {

        departmentService.deleteDepartment(id);

        return "Department deleted successfully";
    }
}