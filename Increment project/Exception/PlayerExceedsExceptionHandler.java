package com.examly.springapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PlayerAlreadyAssignedExceptionHandler {
    @ExceptionHandler(PlayerAlreadyAssignedException.class)
    public ResponseEntity<String> exceptionHandler(PlayerAlreadyAssignedException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }
}
