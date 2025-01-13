package com.healsync.Service;

import com.healsync.dto.DoctorDTO;

import java.util.List;

public interface DoctorService {
     
    DoctorDTO createDoctor(DoctorDTO doctorDTO);

    DoctorDTO updateDoctor(Long id, DoctorDTO updatedDoctorDTO);

    void updateDoctorStatus(Long id, String status);

    DoctorDTO getDoctorById(Long id);

    List<DoctorDTO> getAllDoctors();

    void deleteDoctor(Long id);

    List<DoctorDTO>  getDoctorsBySpecialty(String specialty);

    List<DoctorDTO>  getDoctorsByCity(String city);

    List<DoctorDTO> searchDoctors(String name);

    boolean isDoctorEmailUnique(String email);

}
