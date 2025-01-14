package com.healsync.appointment.Service;

import com.healsync.appointment.dto.AppointmentDTO;

import java.util.List;

public interface AppointmentService {
    AppointmentDTO createAppointment(AppointmentDTO appointmentDTO);

    AppointmentDTO getAppointmentById(Long id);

    List<AppointmentDTO> getAllAppointments();

    AppointmentDTO updateAppointment(Long id, AppointmentDTO appointmentDTO);

    void deleteAppointment(Long id);

    List<AppointmentDTO> getAppointmentsByPatientId(Long patientId);

    List<AppointmentDTO> getAppointmentsByDoctorId(Long doctorId);

    AppointmentDTO rescheduleAppointment(Long id, AppointmentDTO rescheduleRequestDto);
}
