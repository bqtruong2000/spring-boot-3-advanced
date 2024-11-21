package core.identityservice.dto.request;

import core.identityservice.validator.dob.DobConstraint;
import core.identityservice.validator.name.NameConstraint;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 5, message = "USERNAME_INVALID")
    String username;
    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;
    @NameConstraint(min = 2, message = "INVALID_FIRST_NAME")
    String firstName;
    @NameConstraint(min = 2, message = "INVALID_LAST_NAME")
    String lastName;
    @DobConstraint(min = 16, message = "INVALID_DOB")
    LocalDate dob;
}
