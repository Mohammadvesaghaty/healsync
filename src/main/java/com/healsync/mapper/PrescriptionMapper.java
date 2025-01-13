package com.healsync.mapper;

import com.healsync.domain.Prescription;
import com.healsync.dto.PrescriptionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PrescriptionMapper {

    @Mapping(source = "appointment.id", target = "appointmentId")
    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(source = "patient.id", target = "patientId")
    @Mapping(source = "status", target = "status") // Assuming status is an enum
    PrescriptionDTO toDTO(Prescription prescription);
    List<PrescriptionDTO> toDTOList(List<Prescription> prescriptions);

    Prescription toEntity(PrescriptionDTO prescriptionDTO);
    List<Prescription> toEntityList(List<PrescriptionDTO> prescriptionDTOs);
}
