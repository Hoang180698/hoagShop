package com.example.notification_service.controller;

import com.example.event.dto.NotificationEvent;
import com.example.notification_service.service.EmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailController {
    EmailService emailService;

    @KafkaListener(topics = "order-confirmed")
    public void sendEmail(NotificationEvent message) {
        emailService.sendMail(message);
    }
}
