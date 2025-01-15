package com.healsync.notification.service;

import org.springframework.stereotype.Service;

@Service
public class SMSService {
    public void sendSMS(String phoneNumber, String message) {
        System.out.println("sendSMS : "+ phoneNumber+ " - message: "+ message);

        //will fix the implementations later

        /*Twilio.init("your-account-sid", "your-auth-token");
        Message.creator(
                new PhoneNumber(phoneNumber),
                new PhoneNumber("your-twilio-phone-number"),
                message
        ).create();*/
    }
}
