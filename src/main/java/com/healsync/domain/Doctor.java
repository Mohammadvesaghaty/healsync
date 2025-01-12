package com.healsync.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String specialty;

    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull
    private String password;

    private String phone;

    @NotNull
    private String city;

    @NotNull
    private String zipCode;

    private String address;

    @NotNull
    private LocalDate dateOfBirth;

    // Emergency contact information for the doctor
    private String emergencyContactName;
    private String emergencyContactPhone;

    // Education and qualifications of the doctor
    private String qualifications;

    // Medical license number (if applicable)
    private String medicalLicenseNumber;

    // Hospital/clinic where the doctor works
    private String hospitalName;
    private String clinicName;

    // Availability schedule
    private LocalDateTime availabilityStart;
    private LocalDateTime availabilityEnd;

    // Profile picture URL or path (optional)
    private String profilePictureUrl;

    // A flag to indicate if the doctor is currently accepting new patients
    private boolean acceptingNewPatients;

    // Status of the doctor (active, on leave, retired, etc.)
    @Enumerated(EnumType.STRING)
    private DoctorStatus status;
}

