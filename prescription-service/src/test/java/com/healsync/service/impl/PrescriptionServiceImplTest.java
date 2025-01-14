package com.healsync.service.impl;

import com.healsync.appointment.Service.impl.PrescriptionServiceImpl;
import com.healsync.appointment.domain.Prescription;
import com.healsync.appointment.domain.PrescriptionStatus;
import com.healsync.appointment.dto.PrescriptionDTO;
import com.healsync.appointment.exception.PrescriptionNotFoundException;
import com.healsync.appointment.mapper.PrescriptionMapper;
import com.healsync.appointment.repository.PrescriptionRepository;
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

class PrescriptionServiceImplTest {

    @InjectMocks
    private PrescriptionServiceImpl prescriptionService;

    @Mock
    private PrescriptionRepository prescriptionRepository;

    @Mock
    private PrescriptionMapper prescriptionMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPrescription_shouldSaveAndReturnPrescriptionDTO() {
         PrescriptionDTO prescriptionDTO = new PrescriptionDTO();
        Prescription prescription = new Prescription();
        Prescription savedPrescription = new Prescription();

        when(prescriptionMapper.toEntity(prescriptionDTO)).thenReturn(prescription);
        when(prescriptionRepository.save(prescription)).thenReturn(savedPrescription);
        when(prescriptionMapper.toDTO(savedPrescription)).thenReturn(prescriptionDTO);

        PrescriptionDTO result = prescriptionService.createPrescription(prescriptionDTO);

         
        assertThat(result).isEqualTo(prescriptionDTO);
        verify(prescriptionRepository, times(1)).save(prescription);
    }

    @Test
    void updatePrescription_shouldUpdateAndReturnPrescriptionDTO_whenPrescriptionExists() {
 
        Long id = 1L;
        Prescription existingPrescription = new Prescription();
        PrescriptionDTO updatedPrescriptionDTO = new PrescriptionDTO();
        updatedPrescriptionDTO.setMedicationName("Updated Medication");
        updatedPrescriptionDTO.setStatus(PrescriptionStatus.FULFILLED.name());

        when(prescriptionRepository.findById(id)).thenReturn(Optional.of(existingPrescription));
        when(prescriptionMapper.toDTO(existingPrescription)).thenReturn(updatedPrescriptionDTO);
        when(prescriptionRepository.save(existingPrescription)).thenReturn(existingPrescription);

         
        PrescriptionDTO result = prescriptionService.updatePrescription(id, updatedPrescriptionDTO);
        assertThat(result).isEqualTo(updatedPrescriptionDTO);
        verify(prescriptionRepository, times(1)).save(existingPrescription);
    }

    @Test
    void updatePrescription_shouldThrowPrescriptionNotFoundException_whenPrescriptionDoesNotExist() {
 
        Long id = 1L;
        PrescriptionDTO updatedPrescriptionDTO = new PrescriptionDTO();

        when(prescriptionRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(PrescriptionNotFoundException.class, () -> prescriptionService.updatePrescription(id, updatedPrescriptionDTO));
    }

    @Test
    void getPrescriptionById_shouldReturnPrescriptionDTO_whenPrescriptionExists() {
 
        Long id = 1L;
        Prescription prescription = new Prescription();
        PrescriptionDTO prescriptionDTO = new PrescriptionDTO();

        when(prescriptionRepository.findById(id)).thenReturn(Optional.of(prescription));
        when(prescriptionMapper.toDTO(prescription)).thenReturn(prescriptionDTO);

         
        PrescriptionDTO result = prescriptionService.getPrescriptionById(id);
        assertThat(result).isEqualTo(prescriptionDTO);
    }

    @Test
    void getPrescriptionById_shouldThrowPrescriptionNotFoundException_whenPrescriptionDoesNotExist() {
 
        Long id = 1L;

        when(prescriptionRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(PrescriptionNotFoundException.class, () -> prescriptionService.getPrescriptionById(id));
    }

    @Test
    void getAllPrescriptions_shouldReturnListOfPrescriptionDTOs() {
 
        List<Prescription> prescriptions = List.of(new Prescription(), new Prescription());
        List<PrescriptionDTO> prescriptionDTOs = List.of(new PrescriptionDTO(), new PrescriptionDTO());

        when(prescriptionRepository.findAll()).thenReturn(prescriptions);
        when(prescriptionMapper.toDTO(any(Prescription.class))).thenReturn(prescriptionDTOs.get(0), prescriptionDTOs.get(1));



        List<PrescriptionDTO> result = prescriptionService.getAllPrescriptions();
        assertThat(result).isEqualTo(prescriptionDTOs);
    }

    @Test
    void deletePrescription_shouldDeletePrescription_whenPrescriptionExists() {
 
        Long id = 1L;

        when(prescriptionRepository.existsById(id)).thenReturn(true);

        prescriptionService.deletePrescription(id);


        verify(prescriptionRepository, times(1)).deleteById(id);
    }

    @Test
    void deletePrescription_shouldThrowPrescriptionNotFoundException_whenPrescriptionDoesNotExist() {
 
        Long id = 1L;
        when(prescriptionRepository.existsById(id)).thenReturn(false);


        assertThrows(PrescriptionNotFoundException.class, () -> prescriptionService.deletePrescription(id));
    }

    @Test
    void getPrescriptionsByPatientId_shouldReturnListOfPrescriptionDTOs() {
 
        Long patientId = 1L;
        List<Prescription> prescriptions = List.of(new Prescription(), new Prescription());
        List<PrescriptionDTO> prescriptionDTOs = List.of(new PrescriptionDTO(), new PrescriptionDTO());

        when(prescriptionRepository.findByPatientId(patientId)).thenReturn(prescriptions);
        when(prescriptionMapper.toDTO(any(Prescription.class))).thenReturn(prescriptionDTOs.get(0), prescriptionDTOs.get(1));

         
        List<PrescriptionDTO> result = prescriptionService.getPrescriptionsByPatientId(patientId);
        assertThat(result).isEqualTo(prescriptionDTOs);
    }

    @Test
    void getPrescriptionsByDoctorId_shouldReturnListOfPrescriptionDTOs() {
 
        Long doctorId = 1L;
        List<Prescription> prescriptions = List.of(new Prescription(), new Prescription());
        List<PrescriptionDTO> prescriptionDTOs = List.of(new PrescriptionDTO(), new PrescriptionDTO());

        when(prescriptionRepository.findByDoctorId(doctorId)).thenReturn(prescriptions);
        when(prescriptionMapper.toDTO(any(Prescription.class))).thenReturn(prescriptionDTOs.get(0), prescriptionDTOs.get(1));

        List<PrescriptionDTO> result = prescriptionService.getPrescriptionsByDoctorId(doctorId);
        assertThat(result).isEqualTo(prescriptionDTOs);
    }

    @Test
    void getPrescriptionsByAppointmentId_shouldReturnListOfPrescriptionDTOs() {
 
        Long appointmentId = 1L;
        List<Prescription> prescriptions = List.of(new Prescription(), new Prescription());
        List<PrescriptionDTO> prescriptionDTOs = List.of(new PrescriptionDTO(), new PrescriptionDTO());

        when(prescriptionRepository.findByAppointmentId(appointmentId)).thenReturn(prescriptions);
        when(prescriptionMapper.toDTO(any(Prescription.class))).thenReturn(prescriptionDTOs.get(0), prescriptionDTOs.get(1));

        List<PrescriptionDTO> result = prescriptionService.getPrescriptionsByAppointmentId(appointmentId);



        assertThat(result).isEqualTo(prescriptionDTOs);
    }

    @Test
    void getPrescriptionsByStatus_shouldReturnListOfPrescriptionDTOs() {
 
        String status = "ACTIVE";
        List<Prescription> prescriptions = List.of(new Prescription(), new Prescription());
        List<PrescriptionDTO> prescriptionDTOs = List.of(new PrescriptionDTO(), new PrescriptionDTO());

        when(prescriptionRepository.findByStatus(status)).thenReturn(prescriptions);
        when(prescriptionMapper.toDTO(any(Prescription.class))).thenReturn(prescriptionDTOs.get(0), prescriptionDTOs.get(1));



        List<PrescriptionDTO> result = prescriptionService.getPrescriptionsByStatus(status);
        assertThat(result).isEqualTo(prescriptionDTOs);
    }
}
