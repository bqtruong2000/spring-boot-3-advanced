package core.identityservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error"),
    INVALID_KEY(1000, "Invalid message key"),
    USER_EXIST(1002, "User already exist"),
    USERNAME_INVALID(1003, "Username must be at least 5 characters"),
    PASSWORD_INVALID(1004, "Password must be at least 8 characters");
    private final int code;
    private final String message;
}
