package com.hellorin.stickyMoss.facade.jobHunting.exceptions;

import com.hellorin.stickyMoss.jobHunting.exceptions.ApplicantNotFoundException;
import com.hellorin.stickyMoss.jobHunting.exceptions.JobApplicationNotFoundException;
import com.hellorin.stickyMoss.jobHunting.exceptions.JobOfferNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by hellorin on 14.09.17.
 */
@ControllerAdvice
public class RestResponseEntityBusinessExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {JobApplicationNotFoundException.class, JobOfferNotFoundException.class, ApplicantNotFoundException.class})
    public ResponseEntity<Object> handleJobApplicationNotFoundException(RuntimeException ex, WebRequest request) throws Exception {
        String bodyOfResponse = "Error";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
