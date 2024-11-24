package com.coinmasters.exceptions;

public class UserNotAMemberOfTheGroupException extends IllegalStateException {
    public UserNotAMemberOfTheGroupException(String errorMessage) {
        super(errorMessage);
    }
}
