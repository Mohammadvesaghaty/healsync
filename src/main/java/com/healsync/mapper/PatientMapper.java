package com.healsync.mapper;

import com.healsync.domain.Patient;
import com.healsync.dto.PatientDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    @Mapping(source = "status", target = "status")
    PatientDTO toDTO(Patient patient);

    List<PatientDTO> toDTOList(List<Patient> patients);

    Patient toEntity(PatientDTO patientDTO);

    List<Patient> toEntityList(List<PatientDTO> patientDTOs);
}
