package com.healsync.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String name;

    private String phone;

    @NotNull
    private String city;

    @NotNull
    private String zipCode;

    private String address;

    @NotNull
    private LocalDate dateOfBirth;

    // Emergency contact
    private String emergencyContactName;
    private String emergencyContactPhone;

    // Insurance information (if applicable)
    private String insuranceProvider;
    private String insurancePolicyNumber;

    // Medical history
    private String medicalHistory;

    // Known allergies (can be comma-separated or a separate entity if needed)
    private String allergies;

    // A flag to track if the patient has consented to treatment
    private boolean treatmentConsent;

    // Status of the patient (active, inactive)
    @Enumerated(EnumType.STRING)
    private PatientStatus status;

    // Optional: Profile picture URL or path
    private String profilePictureUrl;
}

