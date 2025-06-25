package com.BNKBankApp.exception;

public class InvalidTransactionPinException extends RuntimeException {
    public InvalidTransactionPinException(String message) {
        super(message);
    }
}
