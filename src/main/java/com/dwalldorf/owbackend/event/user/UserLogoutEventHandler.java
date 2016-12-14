package com.dwalldorf.owbackend.event.user;

import static com.dwalldorf.owbackend.Application.appInfoMarker;

import com.dwalldorf.owbackend.annotation.Log;
import org.slf4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserLogoutEventHandler {

    @Log
    private Logger logger;

    @EventListener
    public void handleUserLogoutEvent(UserLogoutEvent event) {
        logger.info(appInfoMarker, "User {} logged out", event.getUser().getId());
    }
}
