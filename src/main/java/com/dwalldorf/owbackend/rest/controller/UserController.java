package com.dwalldorf.owbackend.rest.controller;

import com.dwalldorf.owbackend.annotation.RequireAdmin;
import com.dwalldorf.owbackend.annotation.RequireLogin;
import com.dwalldorf.owbackend.exception.InvalidInputException;
import com.dwalldorf.owbackend.model.User;
import com.dwalldorf.owbackend.rest.dto.ListDto;
import com.dwalldorf.owbackend.rest.dto.LoginDto;
import com.dwalldorf.owbackend.service.UserService;
import javax.inject.Inject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UserController.URI_BASE)
public class UserController {

    public static final String URI_BASE = "/users";

    public static final String URI_LOGIN = "/login";
    public static final String URI_ME = "/me";
    public static final String URI_LOGOUT = "/logout";

    private final UserService userService;

    @Inject
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> register(@RequestBody User user) {
        User persistedUser = userService.register(user);
        return new ResponseEntity<>(persistedUser, HttpStatus.CREATED);
    }

    @PostMapping(URI_LOGIN)
    public ResponseEntity<User> login(@RequestBody LoginDto loginDto) throws InvalidInputException {
        User loginUser = userService.login(loginDto.getUsername(), loginDto.getPassword());
        if (loginUser == null) {
            throw new InvalidInputException();
        }

        return new ResponseEntity<>(loginUser, HttpStatus.OK);
    }

    @GetMapping(URI_ME)
    @RequireLogin
    public User getMe() {
        return userService.getCurrentUser();
    }

    @PostMapping(URI_LOGOUT)
    @RequireLogin
    public ResponseEntity logout() {
        userService.logout();
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping
    @RequireAdmin
    public ListDto<User> getAllUsers() {
        return new ListDto<>(userService.getUsers());
    }
}
