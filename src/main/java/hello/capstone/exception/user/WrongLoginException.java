package hello.capstone.exception.user;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongLoginException extends RuntimeException{

    private String message;

    public WrongLoginException(String message) {
        super(message);
        this.message = message;
    }
}
