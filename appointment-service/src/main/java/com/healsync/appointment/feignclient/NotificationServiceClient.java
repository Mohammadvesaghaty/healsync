package com.healsync.appointment.feignclient;

import com.healsync.appointment.dto.NotificationRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "notification-service", url = "/api/v1/notifications")
public interface NotificationServiceClient {

    @GetMapping("/send")
    void sendNotification(NotificationRequestDto notificationRequestDto);


}
