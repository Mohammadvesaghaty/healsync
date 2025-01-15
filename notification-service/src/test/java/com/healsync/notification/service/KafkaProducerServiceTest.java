package com.healsync.notification.service;

import com.healsync.notification.domain.NotificationRequest;
import com.healsync.notification.domain.Topics;
import com.healsync.notification.messages.SystemMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class KafkaProducerServiceTest {

    @Mock
    private KafkaTemplate<String, NotificationRequest> kafkaTemplate;

    @InjectMocks
    private KafkaProducerService kafkaProducerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSendEventToKafka() {
        // Arrange
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setTopic(Topics.NOTIFICATION_TOPIC);
        notificationRequest.setRecipient("test@example.com");
        notificationRequest.setSubject("Test Subject");
        notificationRequest.setBody("This is a test message.");

        // Act
        kafkaProducerService.sendEvent(notificationRequest);

        verify(kafkaTemplate, times(1)).send(Topics.NOTIFICATION_TOPIC, notificationRequest);
    }

    @Test
    void shouldNotSendEventWhenRequestIsNull() {
        // Arrange
        NotificationRequest notificationRequest = null;

        // Act & Assert

        IllegalArgumentException exception=assertThrows(
                IllegalArgumentException.class,
                ()-> kafkaProducerService.sendEvent(notificationRequest)
        );

        assertEquals(SystemMessages.NotificationShouldNoBeNull,exception.getMessage());

        verifyNoInteractions(kafkaTemplate); // Verify that KafkaTemplate was not called
    }
}
