package com.dwalldorf.owbackend.event.user;

import static com.dwalldorf.owbackend.Application.appInfoMarker;

import com.dwalldorf.owbackend.annotation.Log;
import org.slf4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserUploadedDemoEventHandler {

    @Log
    private Logger logger;

    @EventListener
    public void handleUserUploadedDemoEvent(UserUploadedDemoEvent event) {
        logger.info(appInfoMarker, "User {} uploaded demo: {}", event.getUser().getId(), event.getDemoFile().getId());
    }
}
