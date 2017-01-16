package com.dwalldorf.owbackend.worker;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MINUTES;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import com.dwalldorf.owbackend.BaseTest;
import com.dwalldorf.owbackend.model.OverwatchUserScore;
import com.dwalldorf.owbackend.model.OverwatchUserScore.Period;
import com.dwalldorf.owbackend.service.OverwatchUserScoreService;
import com.dwalldorf.owbackend.service.OverwatchVerdictService;
import com.dwalldorf.owbackend.service.StopWatch;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.ApplicationEventPublisher;

public class OverwatchScoreWorkerTest extends BaseTest {

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Mock
    private OverwatchUserScoreService scoreService;

    @Mock
    private OverwatchVerdictService verdictService;

    @Mock
    private StopWatch stopWatch;

    private OverwatchScoreWorker worker;

    @Override
    protected void afterSetup() {
        this.worker = new OverwatchScoreWorker(eventPublisher, scoreService, verdictService, stopWatch);
    }

    @Test
    public void testProcessUserScores_NewVerdictsSinceLastRun() throws Exception {
        OverwatchUserScore latestScore = Mockito.mock(OverwatchUserScore.class);
        Date latestScoreDate = Date.from(Instant.now().minus(1, DAYS));
        when(latestScore.getCalculated()).thenReturn(latestScoreDate);

        when(scoreService.getLatestScoreByPeriod(eq(Period.DAILY))).thenReturn(Optional.of(latestScore));
        when(verdictService.hasVerdictsAfter(eq(latestScoreDate))).thenReturn(true);

        worker.processUserScoresDaily();

        verify(verdictService).hasVerdictsAfter(eq(latestScoreDate));
        verify(scoreService).reprocessUserScores(any());
    }

    @Test
    public void testProcessUserScores_NoNewerVerdicts() throws Exception {
        OverwatchUserScore latestScore = Mockito.mock(OverwatchUserScore.class);
        Date latestScoreDate = Date.from(Instant.now().minus(1, MINUTES));
        when(latestScore.getCalculated()).thenReturn(latestScoreDate);

        when(scoreService.getLatestScoreByPeriod(eq(Period.DAILY))).thenReturn(Optional.of(latestScore));
        when(verdictService.hasVerdictsAfter(eq(latestScoreDate))).thenReturn(false);

        worker.processUserScoresDaily();

        verify(verdictService).hasVerdictsAfter(eq(latestScoreDate));
        verify(scoreService, never()).reprocessUserScores(any());
    }
}