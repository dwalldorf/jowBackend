package com.dwalldorf.owbackend.event;

import static com.dwalldorf.owbackend.Application.appInfoMarker;

import com.dwalldorf.owbackend.annotation.Log;
import org.slf4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class OverwatchVerdictScoresProcessedEventHandler {

    @Log
    private Logger logger;

    @EventListener
    public void handleOverwatchVerdictScoresProcessedEvent(OverwatchVerdictScoresProcessedEvent event) {
        String preLogMessage = "";

        if (event.isInitialRun()) {
            preLogMessage = "Initial run - ";
        }

        logger.info(
                appInfoMarker,
                "{}processed {} scores in {}ms for period {}",
                preLogMessage,
                event.getProcessedScores(),
                event.getRuntime(),
                event.getPeriod().toString()
        );
    }
}
