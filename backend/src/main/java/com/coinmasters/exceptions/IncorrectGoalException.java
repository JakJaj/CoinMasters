package com.coinmasters.exceptions;

public class IncorrectGoalException extends IllegalArgumentException{
    public IncorrectGoalException(String errorMessage){
        super(errorMessage);
    }
}
