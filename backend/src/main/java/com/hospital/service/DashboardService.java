package com.hospital.service;

import com.hospital.dto.DashboardResponse;
import com.hospital.entity.AppointmentStatus;
import com.hospital.repository.AppointmentRepository;
import com.hospital.repository.DoctorRepository;
import com.hospital.repository.PatientRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;

    public DashboardService(
            PatientRepository patientRepository,
            DoctorRepository doctorRepository,
            AppointmentRepository appointmentRepository) {

        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public DashboardResponse getStats() {

        long totalPatients = patientRepository.count();
        long totalDoctors = doctorRepository.count();
        long totalAppointments = appointmentRepository.count();
        long completedAppointments =
                appointmentRepository.countByStatus(AppointmentStatus.COMPLETED);

        return new DashboardResponse(
                totalPatients,
                totalDoctors,
                totalAppointments,
                completedAppointments
        );
    }
}