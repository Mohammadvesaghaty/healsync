package com.healsync.appointment.controller;

import com.healsync.appointment.dto.DoctorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface DoctorController {

    ResponseEntity<DoctorDTO> getDoctorById(@PathVariable Long id);

    ResponseEntity<List<DoctorDTO>> getAllDoctors();

    ResponseEntity<DoctorDTO> createDoctor(@RequestBody DoctorDTO doctorDTO);

    ResponseEntity<DoctorDTO> updateDoctor(@PathVariable Long id, @RequestBody DoctorDTO doctorDTO);

    ResponseEntity<Void> deleteDoctor(@PathVariable Long id);

    ResponseEntity<List<DoctorDTO>> searchDoctors(String keyword);

    ResponseEntity<Void> updateDoctorStatus(@PathVariable Long id, String status);

    ResponseEntity<List<DoctorDTO>> getDoctorsBySpecialty(@PathVariable String specialty);
}
