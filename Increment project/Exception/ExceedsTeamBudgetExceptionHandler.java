package com.examly.springapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ExceedsTeamBudgetExceptionHandler {
    @ExceptionHandler(ExceedsTeamBudgetException.class)
    public ResponseEntity<String> exceptionHandler(ExceedsTeamBudgetException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }
}
