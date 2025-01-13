package com.healsync.repository;

import com.healsync.domain.Patient;
import com.healsync.domain.PatientStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByEmail(String email);
    List<Patient> findByName(String name);
    List<Patient> findByCityAndZipCode(String city, String zipCode);
    List<Patient> findByDateOfBirthBetween(LocalDate startDate, LocalDate endDate);
    List<Patient> findByStatus(PatientStatus status);
    List<Patient> findByMedicalHistoryContaining(String keyword);
    List<Patient> findByInsuranceProvider(String insuranceProvider);
    List<Patient> findByCity(String city);
    List<Patient> findByCityAndStatus(String city,PatientStatus status);
    List<Patient> findByZipCode(String zipCode);
}
