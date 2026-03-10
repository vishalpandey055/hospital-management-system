package com.hospital.service;

import com.hospital.dto.request.DepartmentRequest;
import com.hospital.dto.response.DepartmentResponse;
import com.hospital.entity.Department;
import com.hospital.exception.ResourceAlreadyExistsException;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.repository.DepartmentRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public DepartmentResponse createDepartment(DepartmentRequest request) {

        departmentRepository.findByName(request.getName())
                .ifPresent(d -> {
                    throw new ResourceAlreadyExistsException("Department already exists");
                });

        Department department = new Department();
        department.setName(request.getName());

        Department saved = departmentRepository.save(department);

        return new DepartmentResponse(saved.getId(), saved.getName());
    }

    public Page<DepartmentResponse> getAllDepartments(int page, int size, String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Page<Department> departments = departmentRepository.findAll(pageable);

        return departments.map(d ->
                new DepartmentResponse(d.getId(), d.getName()));
    }

    public DepartmentResponse getDepartmentById(Long id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        return new DepartmentResponse(department.getId(), department.getName());
    }

    public DepartmentResponse updateDepartment(Long id, DepartmentRequest request) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        department.setName(request.getName());

        Department updated = departmentRepository.save(department);

        return new DepartmentResponse(updated.getId(), updated.getName());
    }

    public void deleteDepartment(Long id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        departmentRepository.delete(department);
    }
}