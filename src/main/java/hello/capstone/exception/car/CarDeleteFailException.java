package hello.capstone.exception.car;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@Getter
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CarDeleteFailException extends RuntimeException{
    private String message;

    public CarDeleteFailException(String message) {
        super(message);
        this.message = message;
    }
}






