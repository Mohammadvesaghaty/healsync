package com.healsync.mapper;

import com.healsync.domain.Appointment;
import com.healsync.dto.AppointmentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(source = "patient.id", target = "patientId")
    @Mapping(source = "paymentStatus", target = "paymentStatus")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "prescription.id", target = "prescriptionId")
    AppointmentDTO toDTO(Appointment appointment);

    List<AppointmentDTO> toDTOList(List<Appointment> appointments);

    Appointment toEntity(AppointmentDTO appointmentDTO);
    List<Appointment> toEntityList(List<AppointmentDTO> appointmentDTOs);
}
