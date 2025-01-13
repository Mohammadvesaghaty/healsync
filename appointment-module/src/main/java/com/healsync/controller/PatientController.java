package com.healsync.controller;

import com.healsync.dto.PatientDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface PatientController {

    ResponseEntity<List<PatientDTO>> getAllPatients();

    ResponseEntity<PatientDTO> getPatientById(@PathVariable Long id);

    ResponseEntity<PatientDTO> getPatientByEmail(@RequestParam String email);

    ResponseEntity<PatientDTO> createPatient(@RequestBody PatientDTO request);

    ResponseEntity<PatientDTO> updatePatient(@PathVariable Long id, @RequestBody PatientDTO updatedPatient);

    ResponseEntity<Void> deletePatient(@PathVariable Long id);

    ResponseEntity<List<PatientDTO>> getPatientsByStatus(@PathVariable String status);

}
