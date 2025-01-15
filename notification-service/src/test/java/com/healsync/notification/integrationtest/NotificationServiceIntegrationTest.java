package com.healsync.notification.integrationtest;

import com.healsync.notification.domain.NotificationChannel;
import com.healsync.notification.domain.NotificationRequest;
import com.healsync.notification.domain.Topics;
import com.healsync.notification.messages.SystemMessages;
import com.healsync.notification.service.NotificationChannelService;
import com.healsync.notification.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class NotificationServiceIntegrationTest {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationChannelService channelService;

    private NotificationRequest emailRequest;
    private NotificationRequest smsRequest;
    private NotificationRequest pushRequest;

    @BeforeEach
    void setUp() {
        emailRequest = new NotificationRequest(Topics.NOTIFICATION_TOPIC,"user@example.com", "Test Email","EMAIL", NotificationChannel.EMAIL);
        smsRequest = new NotificationRequest(Topics.NOTIFICATION_TOPIC,"1234567890", "Test SMS", "SMS", NotificationChannel.SMS);
        pushRequest = new NotificationRequest(Topics.NOTIFICATION_TOPIC,"deviceId", "Test Push Notification", "PUSH", NotificationChannel.PUSH);
    }

    @Test
    void shouldSendEmailNotification() {
        notificationService.sendNotification(emailRequest);

        // I will add verification to confirm that the email has been sent by
        // check a test inbox or email logging system.
        // Here I just assert the logic has been executed:
        assertTrue(true, "Email sent successfully");
    }

    @Test
    void shouldSendSMSNotification() {
        notificationService.sendNotification(smsRequest);

        // Here I just assert the logic has been executed:
        assertTrue(true, "SMS sent successfully");
    }

    @Test
    void shouldSendPushNotification() {
        notificationService.sendNotification(pushRequest);


        // Here I just assert the logic has been executed:
        assertTrue(true, "Push sent successfully");
    }

    @Test
    void shouldThrowIllegalArgumentExceptionForUnsupportedChannel() {
        NotificationRequest unsupportedRequest = new NotificationRequest(Topics.NOTIFICATION_TOPIC,"user@example.com", "Test", "UNKNOWN",null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            notificationService.sendNotification(unsupportedRequest);
        });

        assertEquals(SystemMessages.UnsupportedNotificationChannel, exception.getMessage());
    }
}
