package core.notificationservice.entity;

import core.notificationservice.dto.request.Recipient;
import core.notificationservice.dto.request.Sender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "email")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class Email {
    @MongoId(FieldType.OBJECT_ID)
    @Id
    String id;
    Sender sender;
    String htmlContent;
    String subject;
    List<Recipient> to;
}
