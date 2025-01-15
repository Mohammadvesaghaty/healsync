package com.healsync.notification.controller;

import com.healsync.notification.domain.NotificationRequest;
import com.healsync.notification.messages.SystemMessages;
import com.healsync.notification.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(ApiNames.NotificationControllerHeader)
@RequiredArgsConstructor
public class NotificationController {
    private final KafkaProducerService kafkaProducerService;

    @PostMapping(ApiNames.NotificationControllerSend)
    public ResponseEntity<String> sendNotification(@RequestBody NotificationRequest request) {
        if(!Optional.ofNullable(request.getTopic()).isPresent()){
            return ResponseEntity.badRequest().body(SystemMessages.TopicShouldNotBeNullOrEmpty);
        }

        kafkaProducerService.sendEvent(request);
        return ResponseEntity.ok(SystemMessages.NotificationSentSuccessfully);
    }
}
