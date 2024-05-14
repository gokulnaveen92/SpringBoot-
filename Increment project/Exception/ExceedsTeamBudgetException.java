package com.examly.springapp.exception;

public class ExceedsTeamBudgetException extends RuntimeException {
    ExceedsTeamBudgetException(String msg){
        super(msg);
    }

}
