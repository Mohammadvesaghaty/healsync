package com.healsync.notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healsync.notification.domain.NotificationRequest;
import com.healsync.notification.domain.Topics;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {
    private final NotificationService notificationService;

    @KafkaListener(topics = Topics.NOTIFICATION_TOPIC, groupId = "notification-group")
    public void processEvent(NotificationRequest notificationRequest)  {
        if(notificationRequest==null){
            return;
        }
        notificationService.sendNotification(notificationRequest);
    }

    private NotificationRequest parseMessage(String message) {
        try {
            return new ObjectMapper().readValue(message, NotificationRequest.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
