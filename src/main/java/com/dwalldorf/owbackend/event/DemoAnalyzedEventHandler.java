package com.dwalldorf.owbackend.event;

import static com.dwalldorf.owbackend.Application.appInfoMarker;

import com.dwalldorf.owbackend.annotation.Log;
import com.dwalldorf.owbackend.model.Demo;
import org.slf4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DemoAnalyzedEventHandler {

    @Log
    private Logger logger;

    @EventListener
    public void handleDemoAnalyzedEvent(DemoAnalyzedEvent event) {
        Demo demo = event.getDemo();
        logger.info(appInfoMarker, "Demo {} of user {} analyzed", demo.getId(), demo.getUserId());
    }
}
