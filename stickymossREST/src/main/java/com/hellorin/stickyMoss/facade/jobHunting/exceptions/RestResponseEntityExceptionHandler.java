package com.hellorin.stickyMoss.facade.jobHunting.exceptions;

import com.hellorin.stickyMoss.jobHunting.exceptions.JobApplicationNotFoundException;
import org.hibernate.validator.method.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by hellorin on 13.09.17.
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(org.hibernate.validator.method.MethodConstraintViolationException.class)
    public ResponseEntity<Object> handleMethodConstraintViolationException(RuntimeException ex, WebRequest request) throws Exception {
        String bodyOfResponse = "Error";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
