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
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;

@Ignore
public class OverwatchScoreWorkerTest extends BaseTest {

    private final static int PERIODS_COUNT = Period.values().length;

    @Mock
    private OverwatchUserScoreService scoreService;

    @Mock
    private OverwatchVerdictService verdictService;

    @Mock
    private Logger logger;

    @Mock
    private StopWatch stopWatch;

    @InjectMocks
    private OverwatchScoreWorker worker;

    @Test
    public void testProcessUserScores_FirstRun() throws Exception {
        when(scoreService.getLatestScoreByPeriod(eq(Period.DAILY))).thenReturn(Optional.empty());
        worker.processUserScoresDaily();

        verify(scoreService).reprocessUserScores(eq(Period.DAILY));
        verify(scoreService).reprocessUserScores(eq(Period.WEEKLY));
        verify(scoreService).reprocessUserScores(eq(Period.MONTHLY));
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
        verify(scoreService, times(PERIODS_COUNT)).reprocessUserScores(any());
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