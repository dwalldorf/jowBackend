package com.dwalldorf.owbackend.event.user;

import static com.dwalldorf.owbackend.Application.appInfoMarker;

import com.dwalldorf.owbackend.annotation.Log;
import com.dwalldorf.owbackend.model.User;
import org.slf4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserRegisterEventHandler {

    @Log
    private Logger logger;

    @EventListener
    public void handleUserRegisteredEvent(UserRegisterEvent event) {
        if (!event.hasUser()) {
            logger.error("received registered event without user");
            return;
        }

        User user = event.getUser();
        String username = user.getUserProperties().getUsername();

        logger.info(appInfoMarker, "User '{}' registered: {}", username, user.getId());
    }
}
