package com.healsync.controller.impl;

import com.healsync.Service.PatientService;
import com.healsync.controller.PatientController;
import com.healsync.dto.PatientDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/patients")
public class PatientControllerImpl implements PatientController {

    private final PatientService patientService;

    @Override
    @GetMapping
    public ResponseEntity<List<PatientDTO>> getAllPatients() {
        List<PatientDTO> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable Long id) {
        PatientDTO patient = patientService.getPatientById(id);
        return ResponseEntity.ok(patient);
    }

    @Override
    @GetMapping("/email/{email}")
    public ResponseEntity<PatientDTO> getPatientByEmail(@PathVariable String email) {
        PatientDTO patient = patientService.getPatientByEmail(email);
        return ResponseEntity.ok(patient);
    }

    @Override
    @PostMapping
    public ResponseEntity<PatientDTO> createPatient(  @RequestBody PatientDTO requestDto) {
        PatientDTO createdPatient = patientService.createPatient(requestDto);
        return ResponseEntity.ok(createdPatient);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable Long id,   @RequestBody PatientDTO updatedPatient) {
        PatientDTO patient = patientService.updatePatient(id, updatedPatient);
        return ResponseEntity.ok(patient);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    @Override
    public ResponseEntity<List<PatientDTO>> getPatientsByStatus(@PathVariable String status) {
        List<PatientDTO> patient = patientService.getPatientsByStatus(status);
        return ResponseEntity.ok(patient);
    }

}
