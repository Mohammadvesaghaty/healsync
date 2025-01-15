package com.healsync.notification.service;

import com.healsync.notification.domain.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, NotificationRequest> kafkaTemplate;

    public void sendEvent(NotificationRequest notificationRequest) {
        kafkaTemplate.send(notificationRequest.getTopic(), notificationRequest);
    }
}
