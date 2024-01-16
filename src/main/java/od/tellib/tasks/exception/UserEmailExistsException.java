package od.tellib.tasks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UserEmailExistsException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public UserEmailExistsException() {
        super("Email is already in use!");
    }
}
