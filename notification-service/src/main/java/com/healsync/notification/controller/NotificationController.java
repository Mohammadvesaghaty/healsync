package com.healsync.notification.controller;

import com.healsync.notification.domain.NotificationRequest;
import com.healsync.notification.service.KafkaProducerService;
import com.healsync.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final KafkaProducerService kafkaProducerService;

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody NotificationRequest request) {
        kafkaProducerService.sendEvent(request);
        return ResponseEntity.ok("Notification sent successfully.");
    }
}
