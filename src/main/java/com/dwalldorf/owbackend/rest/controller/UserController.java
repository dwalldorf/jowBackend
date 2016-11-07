package com.dwalldorf.owbackend.rest.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.dwalldorf.owbackend.annotation.RequireAdmin;
import com.dwalldorf.owbackend.annotation.RequireLogin;
import com.dwalldorf.owbackend.dto.LoginDto;
import com.dwalldorf.owbackend.exception.InvalidInputException;
import com.dwalldorf.owbackend.model.User;
import com.dwalldorf.owbackend.service.UserService;
import java.util.List;
import javax.inject.Inject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Inject
    private UserService userService;

    @RequestMapping(method = POST)
    public ResponseEntity<User> register(@RequestBody User user) {
        User persistedUser = userService.register(user);
        return new ResponseEntity<>(persistedUser, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/login", method = POST)
    public ResponseEntity<User> login(@RequestBody LoginDto loginDto) throws InvalidInputException {
        User loginUser = userService.login(loginDto.getUsername(), loginDto.getPassword());
        if (loginUser == null) {
            throw new InvalidInputException();
        }

        return new ResponseEntity<>(loginUser, HttpStatus.OK);
    }

    @RequireLogin
    @RequestMapping("/me")
    public User getMe() {
        return userService.getCurrentUser();
    }

    @RequireLogin
    @RequestMapping(value = "/logout", method = POST)
    public ResponseEntity logout() {
        userService.logout();
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequireAdmin
    @RequestMapping
    public List<User> getAllUsers() {
        return userService.getUsers();
    }
}
