package com.hospital.service;

import com.hospital.dto.DepartmentRequest;
import com.hospital.dto.DepartmentResponse;
import org.springframework.data.domain.Page;

public interface DepartmentService {

    DepartmentResponse createDepartment(DepartmentRequest request);

    Page<DepartmentResponse> getAllDepartments(int page, int size, String sortBy);

    DepartmentResponse getDepartmentById(Long id);

    DepartmentResponse updateDepartment(Long id, DepartmentRequest request);

    void deleteDepartment(Long id);
}