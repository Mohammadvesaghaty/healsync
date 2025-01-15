package com.healsync.notification.service;

import com.healsync.notification.domain.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationChannelService channelService;

    public void sendNotification(NotificationRequest request) {
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
                throw new IllegalArgumentException("Unsupported notification channel");
        }
    }
}
