package com.dwalldorf.owbackend.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OverwatchScoreWorker {

    private final static Logger logger = LoggerFactory.getLogger(OverwatchScoreWorker.class);

    @Scheduled(fixedDelay = 10000)
    public void processUserScores() {

    }
}
