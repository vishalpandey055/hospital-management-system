package com.hospital.controller;

import com.hospital.dto.DepartmentRequest;
import com.hospital.dto.DepartmentResponse;
import com.hospital.service.DepartmentService;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/departments")
@PreAuthorize("hasRole('ADMIN')")
public class DepartmentController {

    private final DepartmentService departmentService;

    // Manual Constructor Injection
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public DepartmentResponse create(@Valid @RequestBody DepartmentRequest request) {
        return departmentService.createDepartment(request);
    }

    // 🔥 PAGINATION ENDPOINT
    @GetMapping
    public Page<DepartmentResponse> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        return departmentService.getAllDepartments(page, size, sortBy);
    }

    @GetMapping("/{id}")
    public DepartmentResponse getById(@PathVariable Long id) {
        return departmentService.getDepartmentById(id);
    }

    @PutMapping("/{id}")
    public DepartmentResponse update(@PathVariable Long id,
                                     @Valid @RequestBody DepartmentRequest request) {
        return departmentService.updateDepartment(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
    }
}