package com.dwalldorf.owbackend.exception;

public class AdminRequiredException extends UserRoleException {

    public AdminRequiredException() {
    }

    public AdminRequiredException(String message) {
        super(message);
    }
}
