package com.healsync.appointment.mapper;

import com.healsync.appointment.domain.Prescription;
import com.healsync.appointment.dto.PrescriptionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PrescriptionMapper {

    @Mapping(source = "status", target = "status") // Assuming status is an enum
    PrescriptionDTO toDTO(Prescription prescription);
    List<PrescriptionDTO> toDTOList(List<Prescription> prescriptions);

    Prescription toEntity(PrescriptionDTO prescriptionDTO);
    List<Prescription> toEntityList(List<PrescriptionDTO> prescriptionDTOs);
}
