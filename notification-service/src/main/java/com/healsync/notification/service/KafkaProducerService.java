package com.healsync.notification.service;

import com.healsync.notification.domain.NotificationRequest;
import com.healsync.notification.messages.SystemMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, NotificationRequest> kafkaTemplate;

    public void sendEvent(NotificationRequest notificationRequest) {
        if (notificationRequest == null) {
            throw new IllegalArgumentException(SystemMessages.NotificationShouldNoBeNull);
        }
        kafkaTemplate.send(notificationRequest.getTopic(), notificationRequest);
    }
}
