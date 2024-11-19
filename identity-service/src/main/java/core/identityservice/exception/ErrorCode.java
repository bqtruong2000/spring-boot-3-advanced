package core.identityservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1000, "Invalid message key", HttpStatus.BAD_REQUEST),
    USER_EXIST(1002, "User already exist",HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least 5 characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1004, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXIST(1005, "User does not exist", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
