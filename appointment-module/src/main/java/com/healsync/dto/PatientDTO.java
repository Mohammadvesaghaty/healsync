package com.healsync.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {

    private Long id;

    private String email;

    private String password;

    private String name;

    private String phone;

    private String city;

    private String zipCode;

    private String address;

    private LocalDate dateOfBirth;

    private String emergencyContactName;

    private String emergencyContactPhone;

    private String insuranceProvider;

    private String insurancePolicyNumber;

    private String medicalHistory;

    private String allergies;

    private boolean treatmentConsent;

    private String status; // Active, Inactive, ...

    private String profilePictureUrl;
}
