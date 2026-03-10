package com.hospital.repository;

import com.hospital.entity.Appointment;
import com.hospital.entity.AppointmentStatus;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByDoctorId(Long doctorId);

    List<Appointment> findByPatientId(Long patientId);

    List<Appointment> findByStatus(AppointmentStatus status);

    long countByStatus(AppointmentStatus status);

    Optional<Appointment> findByDoctorIdAndAppointmentDate(
            Long doctorId, LocalDateTime appointmentDate);

    Optional<Appointment> findByPatientIdAndAppointmentDate(
            Long patientId, LocalDateTime appointmentDate);

    List<Appointment> findByDoctorIdOrderByAppointmentDateDesc(Long doctorId);

    List<Appointment> findByPatientIdOrderByAppointmentDateDesc(Long patientId);

    List<Appointment> findByDoctorId(Long doctorId, Pageable pageable);
}