package com.healsync.notification.service;

import com.healsync.notification.domain.NotificationChannel;
import com.healsync.notification.domain.NotificationRequest;
import com.healsync.notification.domain.Topics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.annotation.EnableKafka;

import static org.mockito.Mockito.*;

@EnableKafka
class KafkaConsumerServiceTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private KafkaConsumerService kafkaConsumerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        kafkaConsumerService = new KafkaConsumerService(notificationService);
    }

    @Test
    void shouldProcessEventAndSendNotification() {
        // Arrange
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setTopic(Topics.NOTIFICATION_TOPIC);
        notificationRequest.setRecipient("test@example.com");
        notificationRequest.setSubject("Test Subject");
        notificationRequest.setBody("Test body message");

        kafkaConsumerService.processEvent(notificationRequest);

        // Assert
        verify(notificationService, times(1)).sendNotification(notificationRequest);
    }

    @Test
    void shouldNotProcessEventIfNotificationRequestIsNull() {
        kafkaConsumerService.processEvent(null);

        verify(notificationService, times(0)).sendNotification(any());
    }
}
