package com.example.notification_service.service;

import com.example.event.dto.NotificationEvent;
import com.example.notification_service.dto.request.EmailRequest;
import com.example.notification_service.dto.request.Recipient;
import com.example.notification_service.dto.request.Sender;
import com.example.notification_service.exception.AppException;
import com.example.notification_service.exception.ErrorCode;
import com.example.notification_service.repository.httpClient.EmailClient;
import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {
    EmailClient emailClient;

    @Value("${brevo.key}")
    @NonFinal
    String apiKey;

    public void sendMail(NotificationEvent message) {
        String htmlContent = "<p>...<p>";
        EmailRequest emailRequest = EmailRequest.builder()
                .sender(Sender.builder()
                        .name("hoagShop")
                        .email("hoang180698@gmail.com")
                        .build())
                .to(List.of(new Recipient("", message.getRecipient())))
                .subject(message.getSubject())
                .htmlContent(htmlContent)
                .build();
        try {
            emailClient.sendEmail(apiKey, emailRequest);
        } catch (FeignException e){
            throw new AppException(ErrorCode.CANNOT_SEND_EMAIL);
        }
    }
}
