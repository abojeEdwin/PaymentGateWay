package com.BNKBankApp.exception;

public class InvalidBalanceException extends RuntimeException {
  public InvalidBalanceException(String message) {
    super(message);
  }
}
