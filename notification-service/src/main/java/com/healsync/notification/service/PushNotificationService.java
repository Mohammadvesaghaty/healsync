package com.healsync.notification.service;

import org.springframework.stereotype.Service;

@Service
public class PushNotificationService {
    public void sendPushNotification(String recipientToken, String message) {
        System.out.println("sendPushNotification : "+ recipientToken+ " - "+ message);

        //will fix the implementations later

        // Use Firebase Admin SDK to send push notifications
       /* Message firebaseMessage = Message.builder()
                .setToken(recipientToken)
                .putData("message", message)
                .build();

        try {
            FirebaseMessaging.getInstance().send(firebaseMessage);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException("Failed to send push notification", e);
        }*/
    }
}
