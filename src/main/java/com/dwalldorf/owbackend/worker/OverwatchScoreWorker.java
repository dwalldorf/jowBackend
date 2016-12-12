package com.dwalldorf.owbackend.worker;

import static com.dwalldorf.owbackend.Application.appInfoMarker;

import com.dwalldorf.owbackend.annotation.Log;
import com.dwalldorf.owbackend.model.OverwatchUserScore;
import com.dwalldorf.owbackend.model.OverwatchUserScore.Period;
import com.dwalldorf.owbackend.service.OverwatchUserScoreService;
import com.dwalldorf.owbackend.service.OverwatchVerdictService;
import com.dwalldorf.owbackend.service.StopWatch;
import java.util.Optional;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Profile("!integration-test")
public class OverwatchScoreWorker {

    @Log
    private Logger logger;

    private final OverwatchUserScoreService scoreService;

    private final OverwatchVerdictService verdictService;

    private final StopWatch stopWatch;

    @Inject
    public OverwatchScoreWorker(OverwatchUserScoreService scoreService, OverwatchVerdictService verdictService, StopWatch stopWatch) {
        this.scoreService = scoreService;
        this.verdictService = verdictService;
        this.stopWatch = stopWatch;
    }

    @Scheduled(fixedDelay = (5 * 1000))
    public void processUserScoresDaily() {
        processUserScores(Period.DAILY);
    }

    @Scheduled(fixedDelay = (30 * 1000))
    public void processUserScoresWeekly() {
        processUserScores(Period.WEEKLY);
    }

    @Scheduled(fixedDelay = (3600 * 1000))
    public void processUserScoresMonthly() {
        processUserScores(Period.MONTHLY);
    }

    private void processUserScores(final Period period) {
        stopWatch.start();

        boolean processScoresFlag = false;
        int processedScores = 0;

        Optional<OverwatchUserScore> latestScore = scoreService.getLatestScoreByPeriod(period);
        if (!latestScore.isPresent()) {
            logger.info(appInfoMarker, "scores for period {} have never been processed", period.toString());
            processScoresFlag = true;
        } else {
            if (verdictService.hasVerdictsAfter(latestScore.get().getCalculated())) {
                processScoresFlag = true;
            }
        }

        if (processScoresFlag) {
            logger.info(appInfoMarker, "(re)processing scores for period {}", period.toString());

            processedScores += scoreService.reprocessUserScores(period);

            stopWatch.stop();
            logger.info(appInfoMarker, "Processed {} '{}' scores in {}ms", processedScores, period.toString(), stopWatch.getRuntime());
        }
    }
}
