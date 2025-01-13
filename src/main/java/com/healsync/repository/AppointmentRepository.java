package com.healsync.repository;

import com.healsync.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByPatient(Patient patient);
    List<Appointment> findByPatientId(Long patientId);

    List<Appointment> findByDoctor(Doctor doctor);
    List<Appointment> findByDate(LocalDate date);
    List<Appointment> findByDateBetween(LocalDate startDate, LocalDate endDate);

    List<Appointment> findByAppointmentTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    List<Appointment> findByStatus(AppointmentStatus status);

    Optional<Appointment> findByDoctorAndPatient(Doctor doctor, Patient patient);

    List<Appointment> findByAppointmentTimeAfter(LocalDateTime currentDateTime);

    List<Appointment> findByAppointmentTime(LocalDateTime appointmentTime);

    List<Appointment> findByPatientStatus(PatientStatus status);

    List<Appointment> findByDoctorAndAppointmentTimeBetween(Doctor doctor, LocalDateTime startTime, LocalDateTime endTime);
    List<Appointment> findByPatientOrderByAppointmentTimeAsc(Patient patient);
}