package ru.mirea.tictactoe.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CustomException extends RuntimeException {
    private HttpStatus errorCode;
    public CustomException(HttpStatus errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
