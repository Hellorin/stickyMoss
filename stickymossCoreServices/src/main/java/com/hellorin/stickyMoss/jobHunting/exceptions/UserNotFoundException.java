package com.hellorin.stickyMoss.jobHunting.exceptions;

/**
 * Created by hellorin on 21.10.17.
 */
public class UserNotFoundException extends ApplicantNotFoundException {
    public UserNotFoundException(String msg) {
        super(msg);
    }

    public UserNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }
}
