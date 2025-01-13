package com.healsync.Service;

import com.healsync.domain.Patient;
import com.healsync.dto.PatientDTO;

import java.util.List;

public interface PatientService {
    PatientDTO createPatient(PatientDTO patientDTO);

    PatientDTO getPatientById(Long id);

    PatientDTO updatePatient(Long id, PatientDTO patientDTO);

    List<PatientDTO> getAllPatients();

    void deletePatient(Long id);

    PatientDTO getPatientByEmail(String email);

    List<PatientDTO> getPatientsByCity(String city);

    List<PatientDTO> getPatientsByZipCode(String zipCode);

    boolean isPatientExistsByEmail(String email);

    List<PatientDTO> getPatientsByStatus(String status);
}
