package com.healsync.notification.service;

import com.healsync.notification.domain.NotificationRequest;
import com.healsync.notification.messages.SystemMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationChannelService channelService;

    public void sendNotification(NotificationRequest request) {
        Optional.ofNullable(request.getChannel())
                .orElseThrow(()->new IllegalArgumentException(SystemMessages.UnsupportedNotificationChannel));
        // Determine channel (Email, SMS, Push)
        switch (request.getChannel()) {
            case EMAIL:
                channelService.sendEmail(request);
                break;
            case SMS:
                channelService.sendSMS(request);
                break;
            case PUSH:
                channelService.sendPushNotification(request);
                break;
            default:
                throw new IllegalArgumentException(SystemMessages.UnsupportedNotificationChannel);
        }
    }
}
