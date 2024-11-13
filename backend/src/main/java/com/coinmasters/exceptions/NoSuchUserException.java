package com.coinmasters.exceptions;

public class NoSuchUserException extends Exception{

    public NoSuchUserException(String errorMessage){
        super(errorMessage);
    }
}
