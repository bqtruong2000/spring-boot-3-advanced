package core.notificationservice.controller;

import core.event.dto.NotificationEvent;
import core.notificationservice.dto.request.Recipient;
import core.notificationservice.dto.request.SendEmailRequest;
import core.notificationservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NotificationController {
    EmailService emailService;

    @KafkaListener(topics = "notification-delivery")
    public void listenNotificationDelivery(NotificationEvent message){
        try {
            log.info("Received message: {}", message);
            emailService.sendEmail(SendEmailRequest.builder()
                            .to(Recipient.builder()
                                    .email(message.getRecipient())
                                    .build())
                            .subject(message.getSubject())
                            .htmlContent(message.getBody())
                    .build());
        } catch (Exception e) {
            log.error("Error while sending email: {}", e.getMessage());
        }
    }
}
