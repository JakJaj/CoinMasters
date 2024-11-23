package com.coinmasters.exceptions;

public class DeletionByNonAdminUserException extends IllegalCallerException{
    public DeletionByNonAdminUserException(String errorMessage){
        super(errorMessage);
    }
}
