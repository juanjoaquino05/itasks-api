package od.tellib.tasks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UsernameTakenException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public UsernameTakenException() {
        super("Username is already taken!");
    }
}
