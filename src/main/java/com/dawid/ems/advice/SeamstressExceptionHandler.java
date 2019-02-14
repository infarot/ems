package com.dawid.ems.advice;

import com.dawid.ems.exception.SeamstressErrorResponse;
import com.dawid.ems.exception.SeamstressNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SeamstressExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<SeamstressErrorResponse> handleException(SeamstressNotFoundException exc){
        SeamstressErrorResponse errorResponse = new SeamstressErrorResponse(HttpStatus.NOT_FOUND.value(),exc.getMessage(),System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<SeamstressErrorResponse> handleException(Exception exc){
        SeamstressErrorResponse errorResponse = new SeamstressErrorResponse(HttpStatus.BAD_REQUEST.value(),"Invalid request - please check data type",System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }

}
