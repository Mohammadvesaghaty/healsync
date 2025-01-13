package com.healsync.mapper;

import com.healsync.domain.Doctor;
import com.healsync.dto.DoctorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    @Mapping(source = "status", target = "status")
    DoctorDTO toDTO(Doctor doctor);
    List<DoctorDTO> toDTOList(List<Doctor> doctors);

    Doctor toEntity(DoctorDTO doctorDTO);
    List<Doctor> toEntityList(List<DoctorDTO> doctorDTOs);
}
