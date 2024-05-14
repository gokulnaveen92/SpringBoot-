package com.examly.springapp.exception;


public class PlayerAlreadyAssignedException extends RuntimeException{
    PlayerAlreadyAssignedException(String msg){
        super(msg);
    }

}
