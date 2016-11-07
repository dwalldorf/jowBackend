package com.dwalldorf.owbackend.exception;

public class LoginRequiredException extends UserRoleException {

    public LoginRequiredException() {
    }

    public LoginRequiredException(String message) {
        super(message);
    }
}
