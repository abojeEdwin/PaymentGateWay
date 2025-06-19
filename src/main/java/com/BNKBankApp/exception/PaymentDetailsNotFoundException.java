package com.BNKBankApp.exception;

public class PaymentDetailsNotFoundException extends RuntimeException {
    public PaymentDetailsNotFoundException(String message) {
        super(message);
    }
}
