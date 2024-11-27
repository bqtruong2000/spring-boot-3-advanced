package core.notificationservice.service;

import core.notificationservice.dto.request.EmailRequest;
import core.notificationservice.dto.request.SendEmailRequest;
import core.notificationservice.dto.request.Sender;
import core.notificationservice.dto.response.EmailResponse;
import core.notificationservice.exception.AppException;
import core.notificationservice.exception.ErrorCode;
import core.notificationservice.repository.httpclient.EmailClient;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {
    EmailClient emailClient;

    String apiKey = "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";

    public EmailResponse sendEmail(SendEmailRequest request){
        EmailRequest emailRequest = EmailRequest.builder()
                .sender(Sender.builder()
                        .name("LiamBqt")
                        .email("liambqt.dev@gmail.com")
                        .build())
                .to(List.of(request.getTo()))
                .htmlContent(request.getHtmlContent())
                .subject(request.getSubject())
                .build();

        try {
            return emailClient.sendEmail(apiKey,emailRequest);
        } catch (Exception e) {
            throw new AppException(ErrorCode.CAN_NOT_SEND_EMAIL);
        }
    }
}
