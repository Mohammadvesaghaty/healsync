package com.healsync.notification.service;

import com.healsync.notification.domain.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationChannelService {
    private final EmailService emailService;
    private final SMSService smsService;
    private final PushNotificationService pushNotificationService;

    public void sendEmail(NotificationRequest request) {
        emailService.sendEmail(request.getRecipient(), request.getSubject(), request.getBody());
    }

    public void sendSMS(NotificationRequest request) {
        smsService.sendSMS(request.getRecipient(), request.getBody());
    }

    public void sendPushNotification(NotificationRequest request) {
        pushNotificationService.sendPushNotification(request.getRecipient(), request.getBody());
    }
}
