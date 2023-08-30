package com.yassirapichallenge.bankingapi.exceptionhandler;

public class InsufficientBalanceException extends Exception{
    public InsufficientBalanceException(String message) {
        super(message);
    }
}
