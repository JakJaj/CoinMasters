package com.coinmasters.exceptions;

public class TransactionDeletionByNonGroupMemberException extends IllegalCallerException {
    public TransactionDeletionByNonGroupMemberException(String errorMessage) {
        super(errorMessage);
    }
}
