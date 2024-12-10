package com.example.hadistore.exceptions;

import com.example.hadistore.dtos.response.ResponseData;
import com.sun.jdi.request.InvalidRequestStateException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.InvalidPathException;
import java.security.InvalidParameterException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseData<String> handleGeneralException(Exception exception) {
        return new ResponseData<>(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }
    @ExceptionHandler
    public ResponseData<String> handleIllegalArgumentException(IllegalArgumentException exception) {
        return new ResponseData<>(HttpStatus.BAD_REQUEST, exception.getMessage());
    }
    @ExceptionHandler
    public ResponseData<String> handleDataNotFoundException(DataNotFoundException exception){
        return new ResponseData<>(HttpStatus.NOT_FOUND, exception.getMessage());
    }
    @ExceptionHandler
    public ResponseData<String> handleInvalidRequestStateException(InvalidRequestStateException exception){
        return new ResponseData<>(HttpStatus.CONFLICT, exception.getMessage());
    }
    @ExceptionHandler
    public ResponseData<String> handleInvalidParameterException(InvalidParameterException exception){
        return new ResponseData<>(HttpStatus.UNPROCESSABLE_ENTITY, exception.getMessage());
    }
}
