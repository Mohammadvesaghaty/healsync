package com.healsync.appointment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequestDto {

    private String topic;       // Email address, phone number, or push token
    private String recipient;       // Email address, phone number, or push token
    private String subject;         // Subject for email notifications
    private String body;            // Main message content
    private NotificationChannel channel; // Notification channel: EMAIL, SMS, or PUSH
}
