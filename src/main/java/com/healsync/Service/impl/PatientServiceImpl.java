package com.healsync.Service.impl;

import com.healsync.domain.Patient;
import com.healsync.domain.PatientStatus;
import com.healsync.dto.PatientDTO;
import com.healsync.exception.DuplicateEmailException;
import com.healsync.exception.PatientNotFoundException;
import com.healsync.mapper.PatientMapper;
import com.healsync.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements com.healsync.Service.PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Transactional
    @Override
    public PatientDTO createPatient(PatientDTO patientDTO) {

        patientRepository.findByEmail(patientDTO.getEmail())
                .orElseThrow(()-> new DuplicateEmailException("Patient with email " + patientDTO.getEmail() + " already exists."));

        Patient patient = patientMapper.toEntity(patientDTO);
        validatePatientDetails(patient);

        patient = patientRepository.save(patient);
        return patientMapper.toDTO(patient);

    }
    @Transactional(readOnly = true)
    @Override
    public PatientDTO getPatientById(Long id) {

        Patient patient = getPatientByPatientID(id);
        return patientMapper.toDTO(patient);
    }

    @Transactional
    @Override
    public PatientDTO updatePatient(Long id, PatientDTO patientDTO) {
        Patient existingPatient = getPatientByPatientID(id);

        existingPatient.setName(patientDTO.getName());
        existingPatient.setEmail(patientDTO.getEmail());
        existingPatient.setPhone(patientDTO.getPhone());
        existingPatient.setCity(patientDTO.getCity());
        existingPatient.setZipCode(patientDTO.getZipCode());
        existingPatient.setAddress(patientDTO.getAddress());
        existingPatient.setDateOfBirth(patientDTO.getDateOfBirth());
        existingPatient.setEmergencyContactName(patientDTO.getEmergencyContactName());
        existingPatient.setEmergencyContactPhone(patientDTO.getEmergencyContactPhone());
        existingPatient.setInsuranceProvider(patientDTO.getInsuranceProvider());
        existingPatient.setInsurancePolicyNumber(patientDTO.getInsurancePolicyNumber());
        existingPatient.setMedicalHistory(patientDTO.getMedicalHistory());
        existingPatient.setAllergies(patientDTO.getAllergies());
        existingPatient.setTreatmentConsent(patientDTO.isTreatmentConsent());
        existingPatient.setStatus(PatientStatus.valueOf(patientDTO.getStatus()));
        existingPatient.setProfilePictureUrl(patientDTO.getProfilePictureUrl());

        validatePatientDetails(existingPatient);
        patientRepository.save(existingPatient);
        return patientMapper.toDTO(existingPatient);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PatientDTO> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();
        return patientMapper.toDTOList(patients);
    }
    @Transactional
    @Override
    public void deletePatient(Long id) {
        Patient patient = getPatientByPatientID(id);
        patientRepository.delete(patient);
    }

    @Transactional(readOnly = true)
    @Override
    public PatientDTO getPatientByEmail(String email) {
        Patient patient = patientRepository.findByEmail(email).orElseThrow(() -> new PatientNotFoundException("Patient not found with email: " + email));
        return patientMapper.toDTO(patient);
    }
    @Transactional(readOnly = true)
    @Override
    public List<PatientDTO> getPatientsByCity(String city) {
        List<Patient> patientList= patientRepository.findByCity(city);
        return patientMapper.toDTOList(patientList);
    }
    @Transactional(readOnly = true)
    @Override
    public List<PatientDTO> getPatientsByZipCode(String zipCode) {
        List<Patient> patientList= patientRepository.findByZipCode(zipCode);
        return patientMapper.toDTOList(patientList);
    }
    @Transactional(readOnly = true)
    @Override
    public boolean isPatientExistsByEmail(String email) {
        return patientRepository.findByEmail(email).isPresent();
    }

    @Transactional(readOnly = true)
    @Override
    public List<PatientDTO> getPatientsByStatus(String status) {
        List<Patient> patientList= patientRepository.findByStatus(PatientStatus.valueOf(status));
        return patientMapper.toDTOList(patientList);
    }

    //tools

    private boolean validatePatientDetails(Patient patient) {
        // for any validation I can use
        return true;
    }

    @Transactional(readOnly = true)
    private Patient getPatientByPatientID(Long id) {
        return patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient not found with id: " + id));
    }
}
