package com.healsync.appointment.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long doctorId;

    @NotNull
    private Long patientId;

    @NotNull
    private LocalDateTime appointmentTime;

    private String reasonForVisit;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status; // Status (e.g., Scheduled, Completed, Cancelled)

    private String notes;

    private String createdBy; // in the feature I can improve and add user with different roles - sometimes appointments will created by dr Secretary

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String updatedBy; // in the feature I can improve and add user with different roles - sometimes appointments will created by dr Secretary

    private boolean isRescheduled;

    private LocalDateTime rescheduledTime;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus; // I will add payment system in the next step

    private String confirmationCode;

    private Long prescriptionId; // if there is any associated prescription for the appointment

    private String referralSource; // how and to whom the patient referred

}
