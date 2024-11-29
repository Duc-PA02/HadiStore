package com.example.hadistore.exceptions;

import com.example.hadistore.dtos.response.ResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseData<String> handleGeneralException(Exception exception) {
        return new ResponseData<>(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }
    @ExceptionHandler
    public ResponseData<String> handleDataNotFoundException(DataNotFoundException exception){
        return new ResponseData<>(HttpStatus.NOT_FOUND, exception.getMessage());
    }
}
