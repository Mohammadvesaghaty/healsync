package com.healsync.appointment.repository;

import com.healsync.appointment.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByPatientId(Long patientId);

    List<Appointment> findByDoctorId(Long doctorId);
    List<Appointment> findByAppointmentTime(LocalDate date);
    List<Appointment> findByAppointmentTimeBetween(LocalDate startDate, LocalDate endDate);

    List<Appointment> findByAppointmentTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    List<Appointment> findByStatus(AppointmentStatus status);
    List<Appointment> findByAppointmentTimeAfter(LocalDateTime currentDateTime);

    List<Appointment> findByAppointmentTime(LocalDateTime appointmentTime);
    List<Appointment> findByPatientIdOrderByAppointmentTimeAsc(Long patientId);

    @Query("SELECT a FROM Appointment a " +
            "WHERE a.doctorId = :doctorId and a.patientId = :patientId ")
    List<Appointment> findByDoctorIdAndPatientId(@Param("doctorId") Long doctorId,@Param("patientId") Long patientId);

    @Query("SELECT a FROM Appointment a " +
            "WHERE a.doctorId = :doctorId and a.appointmentTime between  :startTime  and :endTime")
    List<Appointment> findByDoctorAndAppointmentTimeBetween(@Param("doctorId") Long doctorId, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

}