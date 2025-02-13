package com.id3.app.exception;

import org.springframework.http.HttpStatus;

public class AccountNotFoundException extends ApiException {
    public AccountNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
