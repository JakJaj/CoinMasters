package com.coinmasters.exceptions;

public class AdminDeletingException extends IllegalStateException {
    public AdminDeletingException(String errorMessage) {
        super(errorMessage);
    }
}
