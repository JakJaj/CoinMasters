package com.coinmasters.exceptions;

public class IncorrectCurrencyException extends IllegalArgumentException{
    public IncorrectCurrencyException(String errorMessage){
        super(errorMessage);
    }
}
