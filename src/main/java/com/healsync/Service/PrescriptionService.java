package com.healsync.Service;

import com.healsync.dto.PrescriptionDTO;

import java.util.List;

public interface PrescriptionService {
    
    PrescriptionDTO createPrescription(PrescriptionDTO prescriptionDTO);

    PrescriptionDTO updatePrescription(Long id, PrescriptionDTO updatedPrescriptionDTO);

    PrescriptionDTO getPrescriptionById(Long id);

    List<PrescriptionDTO> getAllPrescriptions();

    void deletePrescription(Long id);

    List<PrescriptionDTO>  getPrescriptionsByPatientId(Long patientId);

    List<PrescriptionDTO>  getPrescriptionsByDoctorId(Long doctorId);

    List<PrescriptionDTO>  getPrescriptionsByAppointmentId(Long appointmentId);

    List<PrescriptionDTO> getPrescriptionsByStatus(String status);
}
