package com.dawid.ems.advice;

import com.dawid.ems.exception.EmsErrorResponse;
import com.dawid.ems.exception.ResourceNotFoundException;
import com.dawid.ems.exception.SeamstressNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EmsExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<EmsErrorResponse> handleException(SeamstressNotFoundException exc) {
        EmsErrorResponse errorResponse = new EmsErrorResponse(HttpStatus.NOT_FOUND.value(), exc.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<EmsErrorResponse> handleException(BadCredentialsException exc) {
        EmsErrorResponse errorResponse = new EmsErrorResponse(HttpStatus.UNAUTHORIZED.value(), exc.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<EmsErrorResponse> handleException(ResourceNotFoundException exc) {
        EmsErrorResponse errorResponse = new EmsErrorResponse(HttpStatus.UNAUTHORIZED.value(), exc.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<EmsErrorResponse> handleException(Exception exc) {
        EmsErrorResponse errorResponse = new EmsErrorResponse(HttpStatus.BAD_REQUEST.value(), exc.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


}
