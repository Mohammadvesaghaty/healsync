package com.healsync.notification.service;

import com.healsync.notification.domain.NotificationRequest;
import com.healsync.notification.domain.NotificationChannel;
import com.healsync.notification.messages.SystemMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class NotificationServiceTest {

    @Mock
    private NotificationChannelService channelService;

    @InjectMocks
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSendEmailNotification() {
        NotificationRequest request = new NotificationRequest();
        request.setChannel(NotificationChannel.EMAIL);
        request.setRecipient("test@example.com");
        request.setSubject("Test Subject");
        request.setBody("This is a test email.");

        notificationService.sendNotification(request);

        // Assert
        verify(channelService, times(1)).sendEmail(request);
        verifyNoMoreInteractions(channelService);
    }

    @Test
    void shouldSendSMSNotification() {
        // Arrange
        NotificationRequest request = new NotificationRequest();
        request.setChannel(NotificationChannel.SMS);
        request.setRecipient("1234567890");
        request.setBody("This is a test SMS.");

        notificationService.sendNotification(request);

        verify(channelService, times(1)).sendSMS(request);
        verifyNoMoreInteractions(channelService);

    }

    @Test
    void shouldSendPushNotification() {
        // Arrange
        NotificationRequest request = new NotificationRequest();
        request.setChannel(NotificationChannel.PUSH);
        request.setRecipient("device_token_123");
        request.setBody("This is a test push notification.");

        // Act
        notificationService.sendNotification(request);

        // Assert
        verify(channelService, times(1)).sendPushNotification(request);
        verifyNoMoreInteractions(channelService);
    }

    @Test
    void shouldThrowExceptionForUnsupportedChannel() {
        // Arrange
        NotificationRequest request = new NotificationRequest();
        request.setChannel(null);// can be null or anything else than accepted channel

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> notificationService.sendNotification(request)
        );

        assertEquals(SystemMessages.UnsupportedNotificationChannel, exception.getMessage());
    }

}
