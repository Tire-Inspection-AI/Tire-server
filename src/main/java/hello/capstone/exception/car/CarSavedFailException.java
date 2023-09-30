package hello.capstone.exception.car;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CarSavedFailException extends  RuntimeException{

    private String message;

    public CarSavedFailException(String message) {
        super(message);
        this.message = message;
    }
}



