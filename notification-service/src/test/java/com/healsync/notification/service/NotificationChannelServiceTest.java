package com.healsync.notification.service;

import com.healsync.notification.domain.NotificationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class NotificationChannelServiceTest {

    @Mock
    private EmailService emailService;

    @Mock
    private SMSService smsService;

    @Mock
    private PushNotificationService pushNotificationService;

    @InjectMocks
    private NotificationChannelService notificationChannelService;

    private NotificationRequest notificationRequest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setting up the test data for notificationRequest
        notificationRequest = new NotificationRequest();
        notificationRequest.setRecipient("test@example.com");
        notificationRequest.setSubject("Test Subject");
        notificationRequest.setBody("Test message body");
    }

    @Test
    public void shouldSendEmail() {
        notificationChannelService.sendEmail(notificationRequest);
        verify(emailService, times(1))
                .sendEmail(notificationRequest.getRecipient(), notificationRequest.getSubject(), notificationRequest.getBody());
    }

    @Test
    public void shouldSendSMS() {
        notificationChannelService.sendSMS(notificationRequest);
        verify(smsService, times(1))
                .sendSMS(notificationRequest.getRecipient(), notificationRequest.getBody());
    }

    @Test
    public void shouldSendPushNotification() {
        notificationChannelService.sendPushNotification(notificationRequest);

        verify(pushNotificationService, times(1))
                .sendPushNotification(notificationRequest.getRecipient(), notificationRequest.getBody());
    }
}
