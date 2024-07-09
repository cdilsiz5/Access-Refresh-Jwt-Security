package com.id3.app.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends ApiException{

    public UnauthorizedException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
