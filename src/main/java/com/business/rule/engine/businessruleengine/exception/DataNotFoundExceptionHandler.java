package com.business.rule.engine.businessruleengine.exception;

import com.business.rule.engine.businessruleengine.model.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DataNotFoundExceptionHandler {

    @ExceptionHandler(value = DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleDataNotFoundException(DataNotFoundException exception) {
        return new ApiError(exception.getMessage());
    }
}
