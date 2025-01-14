package com.healsync.appointment.mapper;

import com.healsync.appointment.domain.Appointment;
import com.healsync.appointment.dto.AppointmentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(source = "paymentStatus", target = "paymentStatus")
    @Mapping(source = "status", target = "status")
    AppointmentDTO toDTO(Appointment appointment);

    List<AppointmentDTO> toDTOList(List<Appointment> appointments);

    Appointment toEntity(AppointmentDTO appointmentDTO);
    List<Appointment> toEntityList(List<AppointmentDTO> appointmentDTOs);
}
