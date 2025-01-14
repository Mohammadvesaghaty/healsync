package com.healsync.service.impl;

import com.healsync.appointment.Service.impl.AppointmentServiceImpl;
import com.healsync.appointment.domain.Appointment;
import com.healsync.appointment.domain.AppointmentStatus;
import com.healsync.appointment.dto.AppointmentDTO;
import com.healsync.appointment.exception.AppointmentNotFoundException;
import com.healsync.appointment.mapper.AppointmentMapper;
import com.healsync.appointment.repository.AppointmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AppointmentServiceImplTest {

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private AppointmentMapper appointmentMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAppointment_shouldSaveAndReturnAppointment_whenDoctorIsAvailable() {
         
        Long doctorId = 1L;
        LocalDateTime appointmentTime = LocalDateTime.now().plusDays(1);
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setDoctorId(doctorId);
        appointmentDTO.setAppointmentTime(appointmentTime);

        Appointment appointment = new Appointment();
        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(mockDoctor(appointmentTime)));
        when(appointmentMapper.toEntity(appointmentDTO)).thenReturn(appointment);
        when(appointmentRepository.save(appointment)).thenReturn(appointment);
        when(appointmentMapper.toDTO(appointment)).thenReturn(appointmentDTO);

         AppointmentDTO result = appointmentService.createAppointment(appointmentDTO);

        // Assert
        assertThat(result).isEqualTo(appointmentDTO);
        verify(appointmentRepository, times(1)).save(appointment);
    }

    @Test
    void createAppointment_shouldThrowDoctorNotAvailableException_whenDoctorIsUnavailable() {
         
        Long doctorId = 1L;
        LocalDateTime appointmentTime = LocalDateTime.now().plusDays(1);
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setDoctorId(doctorId);
        appointmentDTO.setAppointmentTime(appointmentTime);

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(mockDoctor(LocalDateTime.now())));

        // Act & Assert
        assertThrows(DoctorNotAvailableException.class, () -> appointmentService.createAppointment(appointmentDTO));
        verify(appointmentRepository, never()).save(any());
    }

    @Test
    void getAppointmentById_shouldReturnAppointmentDTO_whenAppointmentExists() {
         
        Long appointmentId = 1L;
        Appointment appointment = new Appointment();
        AppointmentDTO appointmentDTO = new AppointmentDTO();

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(appointment));
        when(appointmentMapper.toDTO(appointment)).thenReturn(appointmentDTO);

        // Act
        AppointmentDTO result = appointmentService.getAppointmentById(appointmentId);

        // Assert
        assertThat(result).isEqualTo(appointmentDTO);
    }

    @Test
    void getAppointmentById_shouldThrowAppointmentNotFoundException_whenAppointmentDoesNotExist() {
         
        Long appointmentId = 1L;

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AppointmentNotFoundException.class, () -> appointmentService.getAppointmentById(appointmentId));
    }

    @Test
    void getAllAppointments_shouldReturnListOfAppointmentDTOs() {
         
        List<Appointment> appointments = List.of(new Appointment(), new Appointment());
        List<AppointmentDTO> appointmentDTOs = List.of(new AppointmentDTO(), new AppointmentDTO());

        when(appointmentRepository.findAll()).thenReturn(appointments);
        when(appointmentMapper.toDTOList(appointments)).thenReturn(appointmentDTOs);

        // Act
        List<AppointmentDTO> result = appointmentService.getAllAppointments();

        // Assert
        assertThat(result).isEqualTo(appointmentDTOs);
    }

    @Test
    void deleteAppointment_shouldDeleteAppointment_whenAppointmentExists() {
         
        Long appointmentId = 1L;
        Appointment appointment = new Appointment();

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(appointment));

        // Act
        appointmentService.deleteAppointment(appointmentId);

        // Assert
        verify(appointmentRepository, times(1)).delete(appointment);
    }

    @Test
    void deleteAppointment_shouldThrowAppointmentNotFoundException_whenAppointmentDoesNotExist() {
         
        Long appointmentId = 1L;

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AppointmentNotFoundException.class, () -> appointmentService.deleteAppointment(appointmentId));
    }

    @Test
    void updateAppointment_shouldUpdateAndReturnAppointmentDTO_whenAppointmentExists() {
         
        Long appointmentId = 1L;
        Appointment existingAppointment = new Appointment();
        AppointmentDTO updatedDTO = new AppointmentDTO();
        updatedDTO.setReasonForVisit("Updated Reason");
        updatedDTO.setStatus(AppointmentStatus.CANCELLED.name());

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(existingAppointment));
        when(appointmentMapper.toDTO(existingAppointment)).thenReturn(updatedDTO);
        when(appointmentRepository.save(existingAppointment)).thenReturn(existingAppointment);


        // Act
        AppointmentDTO result = appointmentService.updateAppointment(appointmentId, updatedDTO);

        // Assert
        assertThat(result).isEqualTo(updatedDTO);
        verify(appointmentRepository, times(1)).save(existingAppointment);
    }

    private static Doctor mockDoctor(LocalDateTime appointmentTime) {
        Doctor doctor = new Doctor();
        doctor.setAvailabilityStart(appointmentTime.minusHours(2));
        doctor.setAvailabilityEnd(appointmentTime.plusHours(2));
        return doctor;
    }
}
