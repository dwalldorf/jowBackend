package com.dwalldorf.owbackend.worker;

import com.dwalldorf.owbackend.event.OverwatchVerdictScoresProcessedEvent;
import com.dwalldorf.owbackend.model.OverwatchUserScore;
import com.dwalldorf.owbackend.model.OverwatchUserScore.Period;
import com.dwalldorf.owbackend.service.OverwatchUserScoreService;
import com.dwalldorf.owbackend.service.OverwatchVerdictService;
import com.dwalldorf.owbackend.service.StopWatch;
import java.util.Optional;
import javax.inject.Inject;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Profile("!integration-test")
public class OverwatchScoreWorker {

    private final ApplicationEventPublisher eventPublisher;

    private final OverwatchUserScoreService scoreService;

    private final OverwatchVerdictService verdictService;

    private final StopWatch stopWatch;

    @Inject
    public OverwatchScoreWorker(ApplicationEventPublisher eventPublisher, OverwatchUserScoreService scoreService, OverwatchVerdictService verdictService, StopWatch stopWatch) {
        this.eventPublisher = eventPublisher;
        this.scoreService = scoreService;
        this.verdictService = verdictService;
        this.stopWatch = stopWatch;
    }

    @Scheduled(fixedDelay = (5 * 1000)) // 5sec
    public void processUserScoresDaily() {
        processUserScores(Period.DAILY);
    }

    @Scheduled(fixedDelay = (30 * 1000)) // 30sec
    public void processUserScoresWeekly() {
        processUserScores(Period.WEEKLY);
    }

    @Scheduled(fixedDelay = (900 * 1000)) // 15min
    public void processUserScoresMonthly() {
        processUserScores(Period.MONTHLY);
    }

    private void processUserScores(final Period period) {
        stopWatch.start();

        boolean processScoresFlag = false;
        int processedScores = 0;

        OverwatchVerdictScoresProcessedEvent.Builder eventBuilder = OverwatchVerdictScoresProcessedEvent.builder();
        eventBuilder.setPeriod(period);

        Optional<OverwatchUserScore> latestScore = scoreService.getLatestScoreByPeriod(period);
        if (!latestScore.isPresent()) {
            eventBuilder.setIsInitialRun();
            processScoresFlag = true;
        } else {
            if (verdictService.hasVerdictsAfter(latestScore.get().getCalculated())) {
                processScoresFlag = true;
            }
        }

        if (processScoresFlag) {
            processedScores += scoreService.reprocessUserScores(period);
            stopWatch.stop();

            eventBuilder.setPeriod(period)
                        .setRuntime(stopWatch.getRuntime())
                        .setProcessedScores(processedScores);
            eventPublisher.publishEvent(eventBuilder.build());
        }
    }
}
