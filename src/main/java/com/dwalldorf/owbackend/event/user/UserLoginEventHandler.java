package com.dwalldorf.owbackend.event.user;

import static com.dwalldorf.owbackend.Application.appInfoMarker;

import com.dwalldorf.owbackend.annotation.Log;
import com.dwalldorf.owbackend.model.User;
import com.dwalldorf.owbackend.model.UserProperties;
import com.dwalldorf.owbackend.service.UserService;
import java.util.Date;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserLoginEventHandler {

    @Log
    private Logger logger;

    private final UserService userService;

    @Inject
    public UserLoginEventHandler(UserService userService) {
        this.userService = userService;
    }

    @EventListener
    public void onLoginEvent(UserLoginEvent event) {
        if (!event.hasUser()) {
            logger.error("received login event without user");
            return;
        }

        User user = event.getUser();
        user = setLoginDate(user);

        userService.update(user);

        logger.info(appInfoMarker, "User '{}' logged in", user.getId());
    }

    @EventListener
    public void handleUserLoginFailedEvent(LoginFailedEvent event) {
        logger.info(appInfoMarker, "login failed: {}", event.getLoginName());
    }

    private User setLoginDate(User user) {
        UserProperties properties = user.getUserProperties();
        Date now = new Date();

        properties.setLastLogin(now);
        if (properties.getFirstLogin() == null) {
            properties.setFirstLogin(now);
        }

        return user;
    }
}
