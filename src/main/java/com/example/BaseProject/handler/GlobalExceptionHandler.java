package com.example.BaseProject.handler;

import com.example.BaseProject.exceptions.SpringRedditException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception exception){
        return ResponseEntity
                .badRequest()
                .body(exception.getMessage());

    }
    @ExceptionHandler(SpringRedditException.class)
    public ResponseEntity<?> handleException(SpringRedditException exception){
        return ResponseEntity
                .badRequest()
                .body(exception.getMessage());

    }

}
