package com.bogstepan.simple_bank.statement.exception;

public class RequestException extends RuntimeException {

    public RequestException(String message) {
        super(message);
    }
}
