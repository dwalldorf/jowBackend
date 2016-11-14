package com.dwalldorf.owbackend.rest.controller;

import com.dwalldorf.owbackend.annotation.RequireLogin;
import com.dwalldorf.owbackend.model.Demo;
import com.dwalldorf.owbackend.model.User;
import com.dwalldorf.owbackend.service.DemoService;
import com.dwalldorf.owbackend.service.UserService;
import java.util.List;
import javax.inject.Inject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Inject
    private UserService userService;

    @Inject
    private DemoService demoService;

    @RequireLogin
    @RequestMapping("/demos")
    public ResponseEntity<List<Demo>> getDemosByUser() {
        User currentUser = userService.getCurrentUser();
        List<Demo> demos = demoService.findByUser(currentUser);

        return new ResponseEntity<>(demos, HttpStatus.OK);
    }
}
