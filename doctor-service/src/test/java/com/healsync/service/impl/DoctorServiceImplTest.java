package com.healsync.service.impl;

import com.healsync.appointment.Service.impl.DoctorServiceImpl;
import com.healsync.appointment.domain.Doctor;
import com.healsync.appointment.domain.DoctorStatus;
import com.healsync.appointment.dto.DoctorDTO;
import com.healsync.appointment.exception.DoctorNotFoundException;
import com.healsync.appointment.exception.EmailAlreadyExistsException;
import com.healsync.appointment.mapper.DoctorMapper;
import com.healsync.appointment.repository.DoctorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DoctorServiceImplTest {

    @InjectMocks
    private DoctorServiceImpl doctorService;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private DoctorMapper doctorMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createDoctor_shouldSaveAndReturnDoctor_whenEmailIsUnique() {
         DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setEmail("unique@example.com");

        Doctor doctor = new Doctor();
        Doctor savedDoctor = new Doctor();

        when(doctorRepository.findByEmail(doctorDTO.getEmail())).thenReturn(Optional.empty());
        when(doctorMapper.toEntity(doctorDTO)).thenReturn(doctor);
        when(doctorRepository.save(doctor)).thenReturn(savedDoctor);
        when(doctorMapper.toDTO(savedDoctor)).thenReturn(doctorDTO);

         DoctorDTO result = doctorService.createDoctor(doctorDTO);

         
        assertThat(result).isEqualTo(doctorDTO);
        verify(doctorRepository, times(1)).save(doctor);
    }

    @Test
    void createDoctor_shouldThrowEmailAlreadyExistsException_whenEmailAlreadyExists() {
         
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setEmail("duplicate@example.com");

        when(doctorRepository.findByEmail(doctorDTO.getEmail())).thenReturn(Optional.of(new Doctor()));

        // Act & Assert
        assertThrows(EmailAlreadyExistsException.class, () -> doctorService.createDoctor(doctorDTO));
        verify(doctorRepository, never()).save(any());
    }

    @Test
    void updateDoctor_shouldUpdateAndReturnDoctorDTO_whenDoctorExists() {
         
        Long doctorId = 1L;
        Doctor existingDoctor = new Doctor();
        DoctorDTO updatedDoctorDTO = new DoctorDTO();
        updatedDoctorDTO.setId(doctorId);
        updatedDoctorDTO.setName("Updated Name");
        updatedDoctorDTO.setStatus(DoctorStatus.ACTIVE.name());

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(existingDoctor));
        when(doctorMapper.toDTO(existingDoctor)).thenReturn(updatedDoctorDTO);
        when(doctorRepository.save(existingDoctor)).thenReturn(existingDoctor);

        // Act
        DoctorDTO result = doctorService.updateDoctor(doctorId, updatedDoctorDTO);

         
        assertThat(result).isEqualTo(updatedDoctorDTO);
        verify(doctorRepository, times(1)).save(existingDoctor);
    }

    @Test
    void updateDoctor_shouldThrowDoctorNotFoundException_whenDoctorDoesNotExist() {
        Long doctorId = 1L;
        DoctorDTO updatedDoctorDTO = new DoctorDTO();
        updatedDoctorDTO.setId(doctorId);

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DoctorNotFoundException.class, () -> doctorService.updateDoctor(doctorId, updatedDoctorDTO));
    }

    @Test
    void deleteDoctor_shouldDeleteDoctor_whenDoctorExists() {
        Long doctorId = 1L;

        when(doctorRepository.existsById(doctorId)).thenReturn(true);

        // Act
        doctorService.deleteDoctor(doctorId);

         
        verify(doctorRepository, times(1)).deleteById(doctorId);
    }

    @Test
    void deleteDoctor_shouldThrowDoctorNotFoundException_whenDoctorDoesNotExist() {
         
        Long doctorId = 1L;

        when(doctorRepository.existsById(doctorId)).thenReturn(false);

        // Act & Assert
        assertThrows(DoctorNotFoundException.class, () -> doctorService.deleteDoctor(doctorId));
    }

    @Test
    void getDoctorById_shouldReturnDoctorDTO_whenDoctorExists() {
         
        Long doctorId = 1L;
        Doctor doctor = new Doctor();
        DoctorDTO doctorDTO = new DoctorDTO();

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(doctorMapper.toDTO(doctor)).thenReturn(doctorDTO);

        // Act
        DoctorDTO result = doctorService.getDoctorById(doctorId);

         
        assertThat(result).isEqualTo(doctorDTO);
    }

    @Test
    void getDoctorById_shouldThrowDoctorNotFoundException_whenDoctorDoesNotExist() {
         
        Long doctorId = 1L;

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DoctorNotFoundException.class, () -> doctorService.getDoctorById(doctorId));
    }

    @Test
    void getAllDoctors_shouldReturnListOfDoctorDTOs() {
         
        List<Doctor> doctors = List.of(new Doctor(), new Doctor());
        List<DoctorDTO> doctorDTOs = List.of(new DoctorDTO(), new DoctorDTO());

        when(doctorRepository.findAll()).thenReturn(doctors);
        when(doctorMapper.toDTO(any(Doctor.class))).thenReturn(doctorDTOs.get(0), doctorDTOs.get(1));

        // Act
        List<DoctorDTO> result = doctorService.getAllDoctors();

         
        assertThat(result).isEqualTo(doctorDTOs);
    }

    @Test
    void isDoctorEmailUnique_shouldReturnTrue_whenEmailIsUnique() {
         
        String email = "unique@example.com";

        when(doctorRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act
        boolean result = doctorService.isDoctorEmailUnique(email);

         
        assertThat(result).isTrue();
    }

    @Test
    void isDoctorEmailUnique_shouldReturnFalse_whenEmailExists() {
         
        String email = "duplicate@example.com";

        when(doctorRepository.findByEmail(email)).thenReturn(Optional.of(new Doctor()));

        // Act
        boolean result = doctorService.isDoctorEmailUnique(email);

         
        assertThat(result).isFalse();
    }

    @Test
    void getDoctorsBySpecialty_shouldReturnListOfDoctorDTOs_whenDoctorsExist() {
         
        String specialty = "Cardiology";
        List<Doctor> doctors = List.of(new Doctor(), new Doctor());
        List<DoctorDTO> doctorDTOs = List.of(new DoctorDTO(), new DoctorDTO());

        when(doctorRepository.findBySpecialtyIgnoreCase(specialty)).thenReturn(doctors);
        when(doctorMapper.toDTO(any(Doctor.class))).thenReturn(doctorDTOs.get(0), doctorDTOs.get(1));

        // Act
        List<DoctorDTO> result = doctorService.getDoctorsBySpecialty(specialty);

         
        assertThat(result).isEqualTo(doctorDTOs);
    }

}
