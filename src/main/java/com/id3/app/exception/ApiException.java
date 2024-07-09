package com.id3.app.exception;


import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiException extends RuntimeException{
    private HttpStatus httpStatus;
    public ApiException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
