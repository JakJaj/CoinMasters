package com.coinmasters.exceptions;

public class NoSuchUserException extends IllegalArgumentException{

    public NoSuchUserException(String errorMessage){
        super(errorMessage);
    }
}
