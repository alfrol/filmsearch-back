package ee.taltech.iti0203backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UserValidationError extends RuntimeException {

    public UserValidationError() {
        super("User validation error");
    }

    public UserValidationError(String message) {
        super(message);
    }
}
