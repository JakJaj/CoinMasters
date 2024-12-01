package com.coinmasters.exceptions;

public class IncorrectPasswordException extends IllegalArgumentException{
    public IncorrectPasswordException(String errorMessage){
        super(errorMessage);
    }
}
