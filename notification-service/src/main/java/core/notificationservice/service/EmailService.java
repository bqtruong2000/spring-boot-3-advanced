package core.notificationservice.service;

import core.notificationservice.dto.request.EmailRequest;
import core.notificationservice.dto.request.SendEmailRequest;
import core.notificationservice.dto.request.Sender;
import core.notificationservice.dto.response.EmailResponse;
import core.notificationservice.entity.Email;
import core.notificationservice.exception.AppException;
import core.notificationservice.exception.ErrorCode;
import core.notificationservice.mapper.EmailMapper;
import core.notificationservice.repository.EmailRepository;
import core.notificationservice.repository.httpclient.EmailClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {
    EmailClient emailClient;
    EmailRepository emailRepository;
    EmailMapper emailMapper;


    @NonFinal
    @Value("${notification.email.brevo-apikey}")
    protected String apiKey;

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
            log.info("Api key: {}", apiKey);
            log.info("Sending email: {}", emailRequest);

            Email email = emailMapper.toEmail(emailRequest);
            emailRepository.save(email);

            return emailClient.sendEmail(apiKey,emailRequest);
        } catch (FeignException e) {
            log.error("Error sending email: {}", e.getMessage());
            throw new AppException(ErrorCode.CAN_NOT_SEND_EMAIL);
        }
    }
}
