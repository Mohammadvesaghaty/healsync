package com.healsync.Service.impl;

import com.healsync.Service.PrescriptionService;
import com.healsync.domain.Prescription;
import com.healsync.domain.PrescriptionStatus;
import com.healsync.dto.PrescriptionDTO;
import com.healsync.exception.PrescriptionNotFoundException;
import com.healsync.mapper.PrescriptionMapper;
import com.healsync.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final PrescriptionMapper prescriptionMapper;

    @Transactional
    @Override
    public PrescriptionDTO createPrescription(PrescriptionDTO prescriptionDTO) {
        Prescription prescription = prescriptionMapper.toEntity(prescriptionDTO);
        Prescription savedPrescription = prescriptionRepository.save(prescription);
        return prescriptionMapper.toDTO(savedPrescription);
    }

    @Transactional
    @Override
    public PrescriptionDTO updatePrescription(Long id, PrescriptionDTO updatedPrescriptionDTO) {
        Prescription existingPrescription = getPrescriptionByPrescriptionId( id);

        existingPrescription.setMedicationName(updatedPrescriptionDTO.getMedicationName());
        existingPrescription.setDosage(updatedPrescriptionDTO.getDosage());
        existingPrescription.setFrequency(updatedPrescriptionDTO.getFrequency());
        existingPrescription.setDuration(updatedPrescriptionDTO.getDuration());
        existingPrescription.setAdditionalInstructions(updatedPrescriptionDTO.getAdditionalInstructions());
        existingPrescription.setPrescribedDate(updatedPrescriptionDTO.getPrescribedDate());
        existingPrescription.setExpirationDate(updatedPrescriptionDTO.getExpirationDate());
        existingPrescription.setNotes(updatedPrescriptionDTO.getNotes());
        existingPrescription.setStatus(PrescriptionStatus.valueOf(updatedPrescriptionDTO.getStatus()));
        Prescription updatedPrescription = prescriptionRepository.save(existingPrescription);
        return prescriptionMapper.toDTO(updatedPrescription);
    }

    @Transactional(readOnly = true)
    @Override
    public PrescriptionDTO getPrescriptionById(Long id) {
        Prescription prescription = getPrescriptionByPrescriptionId( id);
        return prescriptionMapper.toDTO(prescription);
    }



    @Transactional(readOnly = true)
    @Override
    public List<PrescriptionDTO> getAllPrescriptions() {
        return prescriptionRepository.findAll()
                .stream()
                .map(prescriptionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deletePrescription(Long id) {
        if (!prescriptionRepository.existsById(id)) {
            throw new PrescriptionNotFoundException("Prescription not found with ID: " + id);
        }
        prescriptionRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PrescriptionDTO> getPrescriptionsByPatientId(Long patientId) {
        return prescriptionRepository.findByPatientId(patientId)
                .stream()
                .map(prescriptionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<PrescriptionDTO> getPrescriptionsByDoctorId(Long doctorId) {
        return prescriptionRepository.findByDoctorId(doctorId)
                .stream()
                .map(prescriptionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<PrescriptionDTO> getPrescriptionsByAppointmentId(Long appointmentId) {
        return prescriptionRepository.findByAppointmentId(appointmentId)
                .stream()
                .map(prescriptionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PrescriptionDTO> getPrescriptionsByStatus(String status) {
        return prescriptionRepository.findByStatus(status)
                .stream()
                .map(prescriptionMapper::toDTO)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    private Prescription getPrescriptionByPrescriptionId(Long id) {
        return prescriptionRepository.findById(id)
                .orElseThrow(() -> new PrescriptionNotFoundException("Prescription not found with ID: " + id));

    }
}
