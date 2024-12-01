package com.coinmasters.exceptions;

public class UserAlreadyExistsException extends IllegalArgumentException{
    public UserAlreadyExistsException(String errorMessage){
        super(errorMessage);
    }
}
