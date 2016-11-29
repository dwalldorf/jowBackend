package com.dwalldorf.owbackend.rest.controller;

import com.dwalldorf.owbackend.annotation.RequireLogin;
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
import org.springframework.web.bind.annotation.RestController;

@RestController("/overwatch/verdicts")
public class OverwatchVerdictController {

    @Inject
    private OverwatchVerdictService verdictService;

    @Inject
    private UserService userService;

    @PostMapping
    @RequireLogin
    public ResponseEntity<OverwatchVerdict> postVerdict(@RequestBody @Valid OverwatchVerdict verdict) {
        verdict.setUserId(userService.getCurrentUser().getId());
        verdict = verdictService.save(verdict);

        return new ResponseEntity<>(verdict, HttpStatus.CREATED);
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
