package com.healsync.repository;

import com.healsync.domain.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    List<Prescription> findByPatientId(Long patientId);
    List<Prescription> findByDoctorId(Long doctorId);
    List<Prescription> findByAppointmentId(Long appointmentId);
    List<Prescription> findByStatus(String status);
    List<Prescription> findByPrescribedDate(LocalDate prescribedDate);
    List<Prescription> findByExpirationDateBefore(LocalDate expirationDate);
    List<Prescription> findByCreatedAtAfter(LocalDate createdAt);
    List<Prescription> findByUpdatedAtAfter(LocalDate updatedAt);
    List<Prescription> findByMedicationName(String medicationName);
    List<Prescription> findByPatientIdAndStatus(Long patientId, String status);
    List<Prescription> findByDoctorIdAndStatus(Long doctorId, String status);
}

