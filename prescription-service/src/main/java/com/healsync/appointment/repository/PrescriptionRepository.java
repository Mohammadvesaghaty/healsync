package com.healsync.appointment.repository;

import com.healsync.appointment.domain.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    List<Prescription> findByStatus(String status);
    List<Prescription> findByPrescribedDate(LocalDate prescribedDate);
    List<Prescription> findByExpirationDateBefore(LocalDate expirationDate);
    List<Prescription> findByCreatedAtAfter(LocalDate createdAt);
    List<Prescription> findByUpdatedAtAfter(LocalDate updatedAt);
    List<Prescription> findByMedicationName(String medicationName);


    @Query("SELECT p FROM Prescription p " +
            "WHERE p.patientId = :patientId")
    List<Prescription> findByPatientId(@Param("patientId") Long patientId );


    @Query("SELECT p FROM Prescription p " +
            "WHERE p.doctorId = :doctorId")
    List<Prescription> findByDoctorId(@Param("doctorId") Long doctorId );

    @Query("SELECT p FROM Prescription p " +
            "WHERE p.appointmentId = :appointmentId")
    List<Prescription> findByAppointmentId(@Param("appointmentId") Long appointmentId );

    @Query("SELECT p FROM Prescription p " +
            "WHERE p.patientId = :patientId and p.status= :status")
    List<Prescription> findByPatientIdAndStatus(@Param("patientId")Long patientId, @Param("status") String status);

    @Query("SELECT p FROM Prescription p " +
            "WHERE p.doctorId = :doctorId and p.status= :status")
    List<Prescription> findByDoctorIdAndStatus(@Param("doctorId")Long doctorId,@Param("status") String status);

}

