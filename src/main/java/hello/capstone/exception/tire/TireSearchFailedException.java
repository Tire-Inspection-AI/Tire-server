package hello.capstone.exception.tire;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class TireSearchFailedException extends RuntimeException{
    private String message;

    public TireSearchFailedException(String message) {
        super(message);
        this.message = message;
    }
}






