package com.hellorin.stickyMoss.jobHunting.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by hellorin on 16.07.17.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ApplicantNotFoundException extends UsernameNotFoundException {
    public ApplicantNotFoundException(String msg) {
        super(msg);
    }

    public ApplicantNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }
}
