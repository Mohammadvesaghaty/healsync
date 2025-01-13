package com.healsync.repository;

import com.healsync.domain.Doctor;
import com.healsync.domain.DoctorStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByEmail(String email);
    List<Doctor> findByName(String name);
    List<Doctor> findByNameContainingIgnoreCase(String name);
    List<Doctor> findBySpecialty(String specialty);
    List<Doctor> findBySpecialtyIgnoreCase(String specialty);
    List<Doctor> findByCityAndZipCode(String city, String zipCode);
    List<Doctor> findByCityIgnoreCase(String city);
    List<Doctor> findByStatus(DoctorStatus status);
    List<Doctor> findByHospitalName(String hospitalName);
    List<Doctor> findByClinicName(String clinicName);
    List<Doctor> findByAvailabilityStartAfter(LocalDateTime startTime);
    List<Doctor> findByAvailabilityEndBefore(LocalDateTime endTime);
    List<Doctor> findByQualificationsContaining(String keyword);
    List<Doctor> findByAcceptingNewPatients(boolean acceptingNewPatients);

    @Query("SELECT d FROM Doctor d WHERE d.specialty = :specialty AND d.acceptingNewPatients = :acceptingNewPatients")
    List<Doctor> findDoctorsBySpecialtyAndNewPatientStatus(
            @Param("specialty") String specialty,
            @Param("acceptingNewPatients") boolean acceptingNewPatients);
}
