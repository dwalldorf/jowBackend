package com.dwalldorf.owbackend.rest.controller;

import com.dwalldorf.owbackend.annotation.RequireLogin;
import com.dwalldorf.owbackend.exception.LoginRequiredException;
import com.dwalldorf.owbackend.exception.NotFoundException;
import com.dwalldorf.owbackend.model.CSGOMap;
import com.dwalldorf.owbackend.model.OverwatchVerdict;
import com.dwalldorf.owbackend.model.User;
import com.dwalldorf.owbackend.rest.dto.ListDto;
import com.dwalldorf.owbackend.service.OverwatchVerdictService;
import com.dwalldorf.owbackend.service.UserService;
import java.util.Arrays;
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
@RequestMapping(OverwatchVerdictController.URI_BASE)
public class OverwatchVerdictController {

    public static final String URI_BASE = "/overwatch";
    public static final String URI_VERDICTS = "/verdicts";
    public final static String URI_MAPS = "/maps";

    private OverwatchVerdictService verdictService;

    private final UserService userService;

    @Inject
    public OverwatchVerdictController(OverwatchVerdictService verdictService, UserService userService) {
        this.verdictService = verdictService;
        this.userService = userService;
    }

    @PostMapping(URI_VERDICTS)
    @RequireLogin
    public ResponseEntity<OverwatchVerdict> postVerdict(@RequestBody @Valid OverwatchVerdict verdict) throws LoginRequiredException {
        return new ResponseEntity<>(verdictService.save(verdict), HttpStatus.CREATED);
    }

    @GetMapping(URI_VERDICTS + "/{userId}")
    @RequireLogin
    public ListDto<OverwatchVerdict> getUserVerdicts(@PathVariable String userId) {
        User user = userService.findById(userId);
        if (user == null) {
            throw new NotFoundException("No such user: " + userId);
        }

        return new ListDto<>(verdictService.findByUser(user));
    }

    @GetMapping(URI_MAPS)
    public ListDto<CSGOMap> getMaps() {
        return new ListDto<>(Arrays.asList(CSGOMap.values()));
    }
}
