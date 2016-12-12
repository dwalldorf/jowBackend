package com.dwalldorf.owbackend.rest.controller;

import com.dwalldorf.owbackend.annotation.RequireLogin;
import com.dwalldorf.owbackend.exception.LoginRequiredException;
import com.dwalldorf.owbackend.exception.NotFoundException;
import com.dwalldorf.owbackend.model.OverwatchVerdict;
import com.dwalldorf.owbackend.model.User;
import com.dwalldorf.owbackend.service.OverwatchVerdictService;
import com.dwalldorf.owbackend.service.UserService;
import java.util.List;
import javax.inject.Inject;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OverwatchVerdictController.URI)
public class OverwatchVerdictController {

    public static final String URI = "/overwatch/verdicts";

    private OverwatchVerdictService verdictService;

    private final UserService userService;

    @Inject
    public OverwatchVerdictController(OverwatchVerdictService verdictService, UserService userService) {
        this.verdictService = verdictService;
        this.userService = userService;
    }

    @PostMapping
    @RequireLogin
    public ResponseEntity<OverwatchVerdict> postVerdict(@RequestBody @Valid OverwatchVerdict verdict) throws LoginRequiredException {
        return new ResponseEntity<>(verdictService.save(verdict), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    @RequireLogin
    public List<OverwatchVerdict> getUserVerdicts(@PathVariable String userId) {
        User user = userService.findById(userId);
        if (user == null) {
            throw new NotFoundException("No such user: " + userId);
        }

        return verdictService.findByUser(user);
    }
}
