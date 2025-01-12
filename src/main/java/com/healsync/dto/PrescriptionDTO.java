package com.healsync.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionDTO {

    private Long id;

    private Long appointmentId;

    private Long doctorId;

    private Long patientId;

    private String medicationName;

    private String dosage;

    private String frequency;

    private String duration;

    private String additionalInstructions;

    private LocalDate prescribedDate;

    private String status; // Enum value like "PENDING", "DISPENSED"

    private LocalDate expirationDate;


    private String notes;


    private LocalDate createdAt;

    private LocalDate updatedAt;
}
