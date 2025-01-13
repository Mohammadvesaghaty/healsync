package com.healsync.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AppointmentDTO {

    private Long id;

    private Long doctorId;

    private Long patientId;

    private LocalDateTime appointmentTime;

    private String reasonForVisit;

    private String status;

    private String notes;

    private String createdBy;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String updatedBy;

    private boolean isRescheduled;

    private LocalDateTime rescheduledTime;

    private String paymentStatus;

    private String confirmationCode;

    private Long prescriptionId;

    private String referralSource;
}

