package com.healsync.service.impl;

import com.healsync.Service.impl.PatientServiceImpl;
import com.healsync.domain.Patient;
import com.healsync.domain.PatientStatus;
import com.healsync.dto.PatientDTO;
import com.healsync.exception.DuplicateEmailException;
import com.healsync.exception.PatientNotFoundException;
import com.healsync.mapper.PatientMapper;
import com.healsync.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PatientServiceImplTest {

    @InjectMocks
    private PatientServiceImpl patientService;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientMapper patientMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPatient_ShouldCreateAndReturnPatient_WhenEmailIsUnique() {
        // Arrange
        PatientDTO patientDTO = new PatientDTO(1L, "test@example.com","password", "John Doe", "1234567890", "City", "12345", "Address", LocalDate.of(1990, 1, 1), "Jane Doe", "9876543210", "Provider", "Policy123", "History", "None", true, "ACTIVE", null);
        Patient patient = new Patient();
        when(patientRepository.findByEmail(patientDTO.getEmail())).thenReturn(Optional.empty());
        when(patientMapper.toEntity(patientDTO)).thenReturn(patient);
        when(patientRepository.save(patient)).thenReturn(patient);
        when(patientMapper.toDTO(patient)).thenReturn(patientDTO);

        // Act
        PatientDTO result = patientService.createPatient(patientDTO);

        // Assert
        assertThat(result).isEqualTo(patientDTO);
        verify(patientRepository).save(patient);
    }

    @Test
    void createPatient_ShouldThrowException_WhenEmailAlreadyExists() {
        PatientDTO patientDTO = new PatientDTO();
        when(patientRepository.findByEmail(patientDTO.getEmail())).thenReturn(Optional.of(new Patient()));

        assertThatThrownBy(() -> patientService.createPatient(patientDTO))
                .isInstanceOf(DuplicateEmailException.class)
                .hasMessageContaining("already exists");
    }

    @Test
    void getPatientById_ShouldReturnPatient_WhenPatientExists() {
        Long patientId = 1L;
        Patient patient = new Patient();
        PatientDTO patientDTO = new PatientDTO();

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));
        when(patientMapper.toDTO(patient)).thenReturn(patientDTO);

       // Act
        PatientDTO result = patientService.getPatientById(patientId);

        // Assert
        assertThat(result).isEqualTo(patientDTO);
    }

    @Test
    void getPatientById_ShouldThrowException_WhenPatientNotFound() {
        Long patientId = 1L;
        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> patientService.getPatientById(patientId))
                .isInstanceOf(PatientNotFoundException.class)
                .hasMessageContaining("not found with id");
    }

    @Test
    void updatePatient_ShouldUpdateAndReturnPatient_WhenPatientExists() {
        Long patientId = 1L;

        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setEmail("newemail@example.com");
        patientDTO.setStatus(PatientStatus.ACTIVE.name());

        Patient patient = new Patient();

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);
        when(patientMapper.toDTO(patient)).thenReturn(patientDTO);



        PatientDTO result = patientService.updatePatient(patientId, patientDTO);

        // Assert
        assertThat(result).isEqualTo(patientDTO);
        verify(patientRepository).save(patient);
    }

    @Test
    void deletePatient_ShouldDeletePatient_WhenPatientExists() {
        Long patientId = 1L;
        Patient patient = new Patient();
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));

        patientService.deletePatient(patientId);
        verify(patientRepository).delete(patient);
    }

    @Test
    void deletePatient_ShouldThrowException_WhenPatientNotFound() {
        Long patientId = 1L;
        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());



        assertThatThrownBy(() -> patientService.deletePatient(patientId))
                .isInstanceOf(PatientNotFoundException.class)
                .hasMessageContaining("not found with id");
    }

    @Test
    void getAllPatients_ShouldReturnListOfPatients() {

        List<Patient> patients = List.of(new Patient(), new Patient());
        List<PatientDTO> patientDTOs = List.of(new PatientDTO(), new PatientDTO());

        when(patientRepository.findAll()).thenReturn(patients);
        when(patientMapper.toDTOList(patients)).thenReturn(patientDTOs);


        List<PatientDTO> result = patientService.getAllPatients();
        assertThat(result).isEqualTo(patientDTOs);
    }

    @Test
    void getPatientByEmail_ShouldReturnPatient_WhenEmailExists() {
        String email = "test@example.com";
        Patient patient = new Patient();
        PatientDTO patientDTO = new PatientDTO();

        when(patientRepository.findByEmail(email)).thenReturn(Optional.of(patient));
        when(patientMapper.toDTO(patient)).thenReturn(patientDTO);


        PatientDTO result = patientService.getPatientByEmail(email);
        assertThat(result).isEqualTo(patientDTO);
    }

    @Test
    void getPatientByEmail_ShouldThrowException_WhenEmailNotFound() {
        String email = "nonexistent@example.com";

        when(patientRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> patientService.getPatientByEmail(email))
                .isInstanceOf(PatientNotFoundException.class)
                .hasMessageContaining("not found with email");
    }
}
