package com.healsync.controller;

import com.healsync.dto.PrescriptionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface PrescriptionController {

    ResponseEntity<PrescriptionDTO> getPrescriptionById(@PathVariable Long id);

    ResponseEntity<List<PrescriptionDTO>> getAllPrescriptions();

    ResponseEntity<PrescriptionDTO> createPrescription(@RequestBody PrescriptionDTO prescriptionDTO);

    ResponseEntity<PrescriptionDTO> updatePrescription(@PathVariable Long id, @RequestBody PrescriptionDTO prescriptionDTO);

    ResponseEntity<Void> deletePrescription(@PathVariable Long id);

    ResponseEntity<List<PrescriptionDTO>> getPrescriptionsByPatientId(@PathVariable Long patientId);

    ResponseEntity<List<PrescriptionDTO>> getPrescriptionsByDoctorId(@PathVariable Long doctorId);

    ResponseEntity<List<PrescriptionDTO>> getPrescriptionsByStatus(@PathVariable String status);
}
