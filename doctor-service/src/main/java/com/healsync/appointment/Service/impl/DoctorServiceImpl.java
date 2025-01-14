package com.healsync.appointment.Service.impl;

import com.healsync.appointment.domain.Doctor;
import com.healsync.appointment.domain.DoctorStatus;
import com.healsync.appointment.dto.DoctorDTO;
import com.healsync.appointment.exception.DoctorNotFoundException;
import com.healsync.appointment.exception.EmailAlreadyExistsException;
import com.healsync.appointment.mapper.DoctorMapper;
import com.healsync.appointment.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DoctorServiceImpl implements com.healsync.appointment.Service.DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    @Transactional
    @Override
    public DoctorDTO createDoctor(DoctorDTO doctorDTO) {
        if(doctorRepository.findByEmail(doctorDTO.getEmail()).isPresent()){
                throw new EmailAlreadyExistsException("Email already exists: " + doctorDTO.getEmail());

        }

        Doctor doctor = doctorMapper.toEntity(doctorDTO);
        Doctor savedDoctor = doctorRepository.save(doctor);
        return doctorMapper.toDTO(savedDoctor);
    }
    @Transactional
    @Override
    public DoctorDTO updateDoctor(Long id, DoctorDTO updatedDoctorDTO) {
        Doctor existingDoctor = getDoctorByDoctorID(updatedDoctorDTO.getId());

        existingDoctor.setName(updatedDoctorDTO.getName());
        existingDoctor.setSpecialty(updatedDoctorDTO.getSpecialty());
        existingDoctor.setEmail(updatedDoctorDTO.getEmail());
        existingDoctor.setPhone(updatedDoctorDTO.getPhone());
        existingDoctor.setCity(updatedDoctorDTO.getCity());
        existingDoctor.setZipCode(updatedDoctorDTO.getZipCode());
        existingDoctor.setAddress(updatedDoctorDTO.getAddress());
        existingDoctor.setDateOfBirth(updatedDoctorDTO.getDateOfBirth());
        existingDoctor.setEmergencyContactName(updatedDoctorDTO.getEmergencyContactName());
        existingDoctor.setEmergencyContactPhone(updatedDoctorDTO.getEmergencyContactPhone());
        existingDoctor.setQualifications(updatedDoctorDTO.getQualifications());
        existingDoctor.setMedicalLicenseNumber(updatedDoctorDTO.getMedicalLicenseNumber());
        existingDoctor.setHospitalName(updatedDoctorDTO.getHospitalName());
        existingDoctor.setClinicName(updatedDoctorDTO.getClinicName());
        existingDoctor.setAvailabilityStart(updatedDoctorDTO.getAvailabilityStart());
        existingDoctor.setAvailabilityEnd(updatedDoctorDTO.getAvailabilityEnd());
        existingDoctor.setAcceptingNewPatients(updatedDoctorDTO.isAcceptingNewPatients());
        existingDoctor.setStatus(DoctorStatus.valueOf(updatedDoctorDTO.getStatus()));
        Doctor updatedDoctor = doctorRepository.save(existingDoctor);
        return doctorMapper.toDTO(updatedDoctor);
    }

    @Transactional
    @Override
    public void updateDoctorStatus(Long id, String status){
        Doctor existingDoctor = getDoctorByDoctorID(id);

        existingDoctor.setStatus(DoctorStatus.valueOf(status));

         doctorRepository.save(existingDoctor);
    }


    @Transactional(readOnly = true)
    @Override
    public DoctorDTO getDoctorById(Long id) {
        Doctor doctor = getDoctorByDoctorID(id);
        return doctorMapper.toDTO(doctor);
    }

    @Transactional(readOnly = true)
    @Override
    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(doctorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteDoctor(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new DoctorNotFoundException("Doctor not found with ID: " + id);
        }
        doctorRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<DoctorDTO> getDoctorsBySpecialty(String specialty) {
        return doctorRepository.findBySpecialtyIgnoreCase(specialty)
                .stream()
                .map(doctorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<DoctorDTO> getDoctorsByCity(String city) {
        return doctorRepository.findByCityIgnoreCase(city)
                .stream()
                .map(doctorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<DoctorDTO> searchDoctors(String name) {
        return doctorRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(doctorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isDoctorEmailUnique(String email) {
        return doctorRepository.findByEmail(email).isEmpty();
    }


    @Transactional(readOnly = true)
    private Doctor getDoctorByDoctorID(Long id) {
        return doctorRepository.findById(id).orElseThrow(() -> new DoctorNotFoundException("Doctor not found with id: " + id));
    }
}

