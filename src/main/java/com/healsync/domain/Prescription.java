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
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @NotNull
    private String medicationName;

    @NotNull
    private String dosage; // usage instructions

    private String frequency;

    private String duration; // Duration of treatment (7 days, 1 month ,...)

    private String additionalInstructions;

    private LocalDate prescribedDate;

    private LocalDate expirationDate;

    private String notes;

    @Enumerated(EnumType.STRING)
    private PrescriptionStatus status;

    private LocalDate createdAt;

    private LocalDate updatedAt; // Timestamp when the prescription was last updated


}
