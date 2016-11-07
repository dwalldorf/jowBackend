package com.dwalldorf.owbackend.rest.controller;

import com.dwalldorf.owbackend.exception.AdminRequiredException;
import com.dwalldorf.owbackend.exception.LoginRequiredException;
import com.dwalldorf.owbackend.model.User;
import com.dwalldorf.owbackend.service.UserService;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class ErrorController {

    private final static Logger logger = LoggerFactory.getLogger(ErrorController.class);

    @Inject
    private UserService userService;

    @ExceptionHandler(LoginRequiredException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleLoginRequireException(LoginRequiredException e) {
        logger.error(e.getMessage());

        return "NOT FOUND";
    }

    @ExceptionHandler(AdminRequiredException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleAdminRequireException(AdminRequiredException e) {
        final String errorMessage = e.getMessage();
        final User currentUser = userService.getCurrentUser();

        if (currentUser != null) {
            logger.error("{} -- by user: {}", errorMessage, currentUser.getId());
        } else {
            logger.error(errorMessage);
        }

        return "NOT FOUND";
    }
}
