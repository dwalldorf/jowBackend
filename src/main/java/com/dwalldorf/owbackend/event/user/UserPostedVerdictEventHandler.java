package com.dwalldorf.owbackend.event.user;

import static com.dwalldorf.owbackend.Application.appInfoMarker;

import com.dwalldorf.owbackend.annotation.Log;
import com.dwalldorf.owbackend.model.OverwatchUserScore;
import com.dwalldorf.owbackend.model.User;
import com.dwalldorf.owbackend.service.OverwatchUserScoreService;
import com.dwalldorf.owbackend.service.UserService;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserPostedVerdictEventHandler {

    @Log
    private Logger logger;

    private final UserService userService;

    private final OverwatchUserScoreService scoreService;

    @Inject
    public UserPostedVerdictEventHandler(UserService userService, OverwatchUserScoreService scoreService) {
        this.userService = userService;
        this.scoreService = scoreService;
    }

    @EventListener
    public void handleUserPostedVerdictEvent(UserPostedVerdictEvent event) {
        User user = event.getUser();
        final String userId = user.getId();

        logger.info(appInfoMarker, "User {} posted new verdict", userId);

        if (!user.getUserProperties().hasPostedVerdict()) {
            user.getUserProperties().setHasPostedVerdict();
            userService.update(user);

            logger.info(appInfoMarker, "User {} posted first verdict", userId);

            List<OverwatchUserScore> userScores = scoreService.findByUser(user);
            if (userScores.size() == 0) {

            }
        }
    }
}
