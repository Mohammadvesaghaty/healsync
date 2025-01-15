package com.healsync.appointment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {

    private Long id;

    private String name;

    private String specialty;

    private String email;

    private String phone;

    private String city;

    private String zipCode;

    private String address;

    private LocalDate dateOfBirth;

    private String emergencyContactName;

    private String emergencyContactPhone;

    private String qualifications;

    private String medicalLicenseNumber;

    private String hospitalName;

    private String clinicName;

    private LocalDateTime availabilityStart;

    private LocalDateTime availabilityEnd;

    private String profilePictureUrl;

    private boolean acceptingNewPatients;

    private String status; // Status of the doctor(Active, On Leave, Retired, ..)
}
