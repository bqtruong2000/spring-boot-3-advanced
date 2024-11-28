package core.notificationservice.repository;

import core.notificationservice.entity.Email;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmailRepository extends MongoRepository<Email, String> {
}
