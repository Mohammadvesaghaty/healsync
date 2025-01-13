package com.healsync.controller.impl;

import com.healsync.Service.AppointmentService;
import com.healsync.controller.AppointmentController;
import com.healsync.dto.AppointmentDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/appointments")
public class AppointmentControllerImpl implements AppointmentController {

    private final AppointmentService appointmentService;

    @Override
    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        List<AppointmentDTO> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getAppointmentById(@PathVariable Long id) {
        AppointmentDTO appointment = appointmentService.getAppointmentById(id);
        return ResponseEntity.ok(appointment);
    }

    @Override
    @PostMapping
    public ResponseEntity<AppointmentDTO> createAppointment( @RequestBody AppointmentDTO request) {
        AppointmentDTO createdAppointment = appointmentService.createAppointment(request);
        return ResponseEntity.ok(createdAppointment);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDTO> updateAppointment(@PathVariable Long id,  @RequestBody AppointmentDTO updatedAppointment) {
        AppointmentDTO appointment = appointmentService.updateAppointment(id, updatedAppointment);
        return ResponseEntity.ok(appointment);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByPatient(@PathVariable Long patientId) {
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByPatientId(patientId);
        return ResponseEntity.ok(appointments);
    }

    @Override
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByDoctor(@PathVariable Long doctorId) {
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByDoctorId(doctorId);
        return ResponseEntity.ok(appointments);
    }

    @Override
    @PutMapping("/{id}/reschedule")
    public ResponseEntity<AppointmentDTO> rescheduleAppointment(@PathVariable Long id, @RequestBody AppointmentDTO rescheduleRequestDto) {
        AppointmentDTO updatedAppointment = appointmentService.rescheduleAppointment(id, rescheduleRequestDto);
        return ResponseEntity.ok(updatedAppointment);
    }
}
