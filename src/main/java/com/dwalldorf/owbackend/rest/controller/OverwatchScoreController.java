package com.dwalldorf.owbackend.rest.controller;

import com.dwalldorf.owbackend.exception.NotFoundException;
import com.dwalldorf.owbackend.model.OverwatchUserScore;
import com.dwalldorf.owbackend.model.OverwatchUserScore.Period;
import com.dwalldorf.owbackend.model.User;
import com.dwalldorf.owbackend.model.internal.PaginationInfo;
import com.dwalldorf.owbackend.rest.dto.ListDto;
import com.dwalldorf.owbackend.service.OverwatchUserScoreService;
import com.dwalldorf.owbackend.service.UserService;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OverwatchVerdictController.URI_BASE)
public class OverwatchScoreController {

    public static final String URI_SCORES = "/scores";

    public static final String URI_SCORES_LOWER = URI_SCORES + "/lower";
    public static final String URI_SCORES_HIGHER = URI_SCORES + "/higher";

    private final UserService userService;

    private final OverwatchUserScoreService scoreService;

    @Inject
    public OverwatchScoreController(UserService userService, OverwatchUserScoreService scoreService) {
        this.userService = userService;
        this.scoreService = scoreService;
    }

    @GetMapping(URI_SCORES + "/{userId}")
    public Serializable getByUser(
            @PathVariable String userId,
            @RequestParam(required = false) Integer period) throws NotFoundException {

        User user = getUser(userId);
        if (period != null) {
            return scoreService.findByUserAndPeriod(user, Period.fromInt(period));
        }
        return new ListDto<>(scoreService.findByUser(user));
    }

    @GetMapping(URI_SCORES_HIGHER + "/{userId}")
    public ListDto<OverwatchUserScore> getHigherThanUser(
            @PathVariable String userId,
            @RequestParam Integer period,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit) throws NotFoundException {

        User user = getUser(userId);
        PaginationInfo paginationInfo = new PaginationInfo(page, limit);
        List<OverwatchUserScore> scores = scoreService.findByHigherThanUser(user, Period.fromInt(period), paginationInfo);

        return new ListDto<>(scores);
    }

    @GetMapping(URI_SCORES_LOWER + "/{userId}")
    public ListDto<OverwatchUserScore> getLowerThanUser(
            @PathVariable String userId,
            @RequestParam Integer period,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit) throws NotFoundException {

        User user = getUser(userId);
        PaginationInfo paginationInfo = new PaginationInfo(page, limit);
        List<OverwatchUserScore> scores = scoreService.findByLowerThanUser(user, Period.fromInt(period), paginationInfo);

        return new ListDto<>(scores);
    }

    private User getUser(final String userId) throws NotFoundException {
        User user = userService.findById(userId);

        if (user == null) {
            throw new NotFoundException("user with id '" + userId + "' does not exist");
        }
        return user;
    }
}
