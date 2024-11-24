package com.coinmasters.exceptions;

public class ActionPerformedByNonAdminUserException extends IllegalCallerException{
    public ActionPerformedByNonAdminUserException(String errorMessage){
        super(errorMessage);
    }
}
