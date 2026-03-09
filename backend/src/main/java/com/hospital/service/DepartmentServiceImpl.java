package com.hospital.service;

import com.hospital.dto.DepartmentRequest;
import com.hospital.dto.DepartmentResponse;
import com.hospital.entity.Department;
import com.hospital.exception.ResourceAlreadyExistsException;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.repository.DepartmentRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements  DepartmentService {

    private final DepartmentRepository departmentRepository;

    // Manual Constructor Injection
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
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

    // 🔥 PAGINATION IMPLEMENTATION
    @Override
    public Page<DepartmentResponse> getAllDepartments(int page, int size, String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Page<Department> departments = departmentRepository.findAll(pageable);

        return departments.map(d ->
                new DepartmentResponse(d.getId(), d.getName()));
    }

    @Override
    public DepartmentResponse getDepartmentById(Long id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        return new DepartmentResponse(department.getId(), department.getName());
    }

    @Override
    public DepartmentResponse updateDepartment(Long id, DepartmentRequest request) {

        Department existing = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        existing.setName(request.getName());

        Department updated = departmentRepository.save(existing);

        return new DepartmentResponse(updated.getId(), updated.getName());
    }

    @Override
    public void deleteDepartment(Long id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        departmentRepository.delete(department);
    }
}