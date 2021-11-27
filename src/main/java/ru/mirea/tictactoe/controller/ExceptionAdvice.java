package ru.mirea.tictactoe.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.mirea.tictactoe.exception.CustomException;
import ru.mirea.tictactoe.exception.ExceptionResponse;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

    @ExceptionHandler({CustomException.class})
    public ResponseEntity<ExceptionResponse> handleCustomException(CustomException ex) {
        ExceptionResponse res = new ExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(res, ex.getErrorCode());
    }

}
