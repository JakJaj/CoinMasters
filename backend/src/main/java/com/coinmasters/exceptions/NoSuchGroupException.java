package com.coinmasters.exceptions;

public class NoSuchGroupException extends IllegalArgumentException{
    public NoSuchGroupException(String errorMessage){
        super(errorMessage);
    }
}
