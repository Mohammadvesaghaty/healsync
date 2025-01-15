package com.healsync.appointment.feignclient;

import com.healsync.appointment.dto.DoctorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "doctor-service")
public interface DoctorServiceClient {

    @GetMapping("/api/v1/doctors/{id}") // Map the endpoint to the method
    Optional<DoctorDTO> getDoctorById(@PathVariable("id") Long id);


}
