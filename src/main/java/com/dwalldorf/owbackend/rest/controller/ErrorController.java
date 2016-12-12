package com.dwalldorf.owbackend.rest.controller;

import static com.dwalldorf.owbackend.Application.appInfoMarker;

import com.dwalldorf.owbackend.annotation.Log;
import com.dwalldorf.owbackend.exception.AdminRequiredException;
import com.dwalldorf.owbackend.exception.LoginRequiredException;
import com.dwalldorf.owbackend.model.User;
import com.dwalldorf.owbackend.service.UserService;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorController {

    private final static String NOT_FOUND = "NOT FOUND";

    @Log
    private Logger logger;

    private final UserService userService;

    @Inject
    public ErrorController(UserService userService) {
        this.userService = userService;
    }

    @ExceptionHandler(LoginRequiredException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleLoginRequireException(LoginRequiredException e) {
        logger.warn(appInfoMarker, e.getMessage());

        return NOT_FOUND;
    }

    @ExceptionHandler(AdminRequiredException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleAdminRequireException(AdminRequiredException e) {
        final String errorMessage = e.getMessage();
        final User currentUser = userService.getCurrentUser();

        if (currentUser != null) {
            logger.warn(appInfoMarker, "{} -- by user: {}", errorMessage, currentUser.getId());
        } else {
            logger.warn(appInfoMarker, errorMessage);
        }

        return NOT_FOUND;
    }
}
