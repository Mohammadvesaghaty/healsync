package com.healsync.appointment.controller;

import com.healsync.appointment.dto.AppointmentDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface AppointmentController {

    ResponseEntity<List<AppointmentDTO>> getAllAppointments();

    ResponseEntity<AppointmentDTO> getAppointmentById(@PathVariable Long id);

    ResponseEntity<AppointmentDTO> createAppointment(@RequestBody AppointmentDTO request);

    ResponseEntity<AppointmentDTO> updateAppointment(@PathVariable Long id, @RequestBody AppointmentDTO updatedAppointment);

    ResponseEntity<Void> deleteAppointment(@PathVariable Long id);

    ResponseEntity<List<AppointmentDTO>> getAppointmentsByPatient(@PathVariable Long patientId);

    ResponseEntity<List<AppointmentDTO>> getAppointmentsByDoctor(@PathVariable Long doctorId);

    ResponseEntity<AppointmentDTO> rescheduleAppointment(@PathVariable Long id, @RequestBody AppointmentDTO rescheduleRequest);
}
