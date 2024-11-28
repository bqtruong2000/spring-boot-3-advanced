package core.notificationservice.mapper;

import core.notificationservice.dto.request.EmailRequest;
import core.notificationservice.entity.Email;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmailMapper {
    Email toEmail(EmailRequest request);

    EmailRequest toEmailRequest(Email email);

}
