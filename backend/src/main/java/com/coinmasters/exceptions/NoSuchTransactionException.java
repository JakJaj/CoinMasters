package com.coinmasters.exceptions;

public class NoSuchTransactionException extends IllegalArgumentException{
    public NoSuchTransactionException(String errorMessage){
        super(errorMessage);
    }
}
