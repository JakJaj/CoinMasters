package com.coinmasters.exceptions;

public class GroupDeletionByNonAdminUserException extends IllegalCallerException{
    public GroupDeletionByNonAdminUserException(String errorMessage){
        super(errorMessage);
    }
}
