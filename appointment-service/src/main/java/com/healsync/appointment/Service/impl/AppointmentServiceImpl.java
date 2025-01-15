package com.healsync.appointment.Service.impl;

import com.healsync.appointment.Service.AppointmentService;
import com.healsync.appointment.domain.Appointment;
import com.healsync.appointment.domain.AppointmentStatus;
import com.healsync.appointment.dto.AppointmentDTO;
import com.healsync.appointment.dto.NotificationChannel;
import com.healsync.appointment.dto.NotificationRequestDto;
import com.healsync.appointment.exception.AppointmentNotFoundException;
import com.healsync.appointment.exception.DoctorNotAvailableException;
import com.healsync.appointment.feignclient.DoctorServiceClient;
import com.healsync.appointment.feignclient.NotificationServiceClient;
import com.healsync.appointment.mapper.AppointmentMapper;
import com.healsync.appointment.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final DoctorServiceClient doctorServiceClient;

    private final AppointmentMapper appointmentMapper;

    private final NotificationServiceClient notificationServiceClient;

    @Transactional
    @Override
    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO) {

        if (!isDoctorAvailable(appointmentDTO.getDoctorId(), appointmentDTO.getAppointmentTime())) {
            throw new DoctorNotAvailableException("Doctor is not available at the specified time");
        }

        Appointment appointment = appointmentMapper.toEntity(appointmentDTO);
        appointment.setCreatedAt(LocalDateTime.now());
        appointment.setUpdatedAt(LocalDateTime.now());
        appointment.setStatus(AppointmentStatus.SCHEDULED); // default status
        appointment = appointmentRepository.save(appointment);

        // Send notification
        NotificationRequestDto notificationRequest = NotificationRequestDto.builder()
                .topic("notification-topic")
                .recipient(appointmentDTO.getPatientId().toString())
                .subject("Appointment Confirmation")
                .body("Your appointment is confirmed for " + appointmentDTO.getAppointmentTime())
                .channel(NotificationChannel.EMAIL)
                .build();

        notificationServiceClient.sendNotification(notificationRequest);

        return appointmentMapper.toDTO(appointment);
    }

    @Transactional(readOnly = true)
    @Override
    public AppointmentDTO getAppointmentById(Long id) {
        Appointment appointment = getAppointmentByAppointmentId(id);
        return appointmentMapper.toDTO(appointment);
    }


    @Transactional(readOnly = true)
    @Override
    public List<AppointmentDTO> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        return appointmentMapper.toDTOList(appointments);
    }

    @Transactional
    @Override
    public AppointmentDTO updateAppointment(Long id, AppointmentDTO appointmentDTO) {
        return updateAppointmentOrReschedule(id, appointmentDTO,false);
    }



    @Transactional
    @Override
    public void deleteAppointment(Long id) {
        Appointment appointment = getAppointmentByAppointmentId(id);
        appointmentRepository.delete(appointment);
    }

    @Override
    public List<AppointmentDTO> getAppointmentsByPatientId(Long patientId) {
        List<Appointment> appointments = appointmentRepository.findByPatientId(patientId);
        return appointmentMapper.toDTOList(appointments);
    }

    @Override
    public List<AppointmentDTO> getAppointmentsByDoctorId(Long doctorId) {
        List<Appointment> appointments = appointmentRepository.findByDoctorId(doctorId);
        return appointmentMapper.toDTOList(appointments);
    }

    @Transactional
    @Override
    public AppointmentDTO rescheduleAppointment(Long id, AppointmentDTO rescheduleRequestDto) {
        return updateAppointmentOrReschedule(id, rescheduleRequestDto,true);
    }

    private AppointmentDTO updateAppointmentOrReschedule(Long id, AppointmentDTO appointmentDTO, boolean isRescheduled) {
        Appointment existingAppointment = getAppointmentByAppointmentId(id);

        // Update fields
        existingAppointment.setAppointmentTime(appointmentDTO.getAppointmentTime());
        existingAppointment.setReasonForVisit(appointmentDTO.getReasonForVisit());
        existingAppointment.setStatus(AppointmentStatus.valueOf(appointmentDTO.getStatus()));
        existingAppointment.setNotes(appointmentDTO.getNotes());
        existingAppointment.setUpdatedAt(LocalDateTime.now());
        existingAppointment.setUpdatedBy(appointmentDTO.getUpdatedBy());

        if(isRescheduled){
            existingAppointment.setRescheduled(true);
            existingAppointment.setRescheduledTime(appointmentDTO.getRescheduledTime());
        }

        existingAppointment = appointmentRepository.save(existingAppointment);

        return appointmentMapper.toDTO(existingAppointment);
    }

    private boolean isDoctorAvailable(Long doctorId, LocalDateTime appointmentTime) {
        return doctorServiceClient.getDoctorById(doctorId)
                .map(doctor -> doctor.getAvailabilityStart().isBefore(appointmentTime) && doctor.getAvailabilityEnd().isAfter(appointmentTime))
                .orElse(false);
    }


    @Transactional(readOnly = true)
    private Appointment getAppointmentByAppointmentId(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found for id: " + id));
    }
}
