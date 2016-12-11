package com.dwalldorf.owbackend.rest.controller;

import com.dwalldorf.owbackend.exception.NotFoundException;
import com.dwalldorf.owbackend.model.OverwatchUserScore;
import com.dwalldorf.owbackend.model.OverwatchUserScore.Period;
import com.dwalldorf.owbackend.model.User;
import com.dwalldorf.owbackend.model.internal.PaginationInfo;
import com.dwalldorf.owbackend.service.OverwatchUserScoreService;
import com.dwalldorf.owbackend.service.UserService;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/overwatch/scores")
public class OverwatchScoreController {

    private UserService userService;

    private OverwatchUserScoreService scoreService;

    @Inject
    public OverwatchScoreController(UserService userService, OverwatchUserScoreService scoreService) {
        this.userService = userService;
        this.scoreService = scoreService;
    }

    @GetMapping("/{userId}/{periodValue}")
    public OverwatchUserScore getByUser(
            @PathVariable String userId,
            @PathVariable Integer periodValue) throws NotFoundException {

        Period period = getPeriod(periodValue);
        User user = getUser(userId);

        return scoreService.findByUserIdAndPeriod(user, period);
    }

    @GetMapping("/lower/{userId}/{periodValue}")
    public List<OverwatchUserScore> getHigherThanUser(
            @PathVariable String userId,
            @PathVariable Integer periodValue,
            @RequestParam(value = "offset", required = false) Integer offset,
            @RequestParam(value = "limit", required = false) Integer limit) throws NotFoundException {

        Period period = getPeriod(periodValue);
        User user = getUser(userId);

        PaginationInfo paginationInfo = new PaginationInfo(offset, limit);
        return scoreService.findByHigherThanUser(user, period, paginationInfo);
    }

    @GetMapping("/higher/{userId}/{periodValue}")
    public List<OverwatchUserScore> getLowerThanUser(
            @PathVariable String userId,
            @PathVariable Integer periodValue,
            @RequestParam(value = "offset", required = false) Integer offset,
            @RequestParam(value = "limit", required = false) Integer limit) throws NotFoundException {

        Period period = getPeriod(periodValue);
        User user = getUser(userId);

        PaginationInfo paginationInfo = new PaginationInfo(offset, limit);
        return scoreService.findByLowerThanUser(user, period, paginationInfo);
    }

    private Period getPeriod(final Integer periodValue) throws NotFoundException {
        Optional<Period> period = Period.fromInt(periodValue);

        if (!period.isPresent()) {
            throw new NotFoundException("No such period: " + periodValue);
        }
        return period.get();
    }

    private User getUser(final String userId) throws NotFoundException {
        User user = userService.findById(userId);

        if (user == null) {
            throw new NotFoundException("user with id '" + userId + "' does not exist");
        }
        return user;
    }
}
