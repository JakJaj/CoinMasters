package com.coinmasters.exceptions;

public class NothingToChangeException extends IllegalStateException {

    public NothingToChangeException(String errorMessage) {
        super(errorMessage);
    }
}
