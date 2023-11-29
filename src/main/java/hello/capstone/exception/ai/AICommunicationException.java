package hello.capstone.exception.ai;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class AICommunicationException extends RuntimeException {

    private String message;
    public AICommunicationException(String message) {
        super(message);
        this.message = message;
    }
}
