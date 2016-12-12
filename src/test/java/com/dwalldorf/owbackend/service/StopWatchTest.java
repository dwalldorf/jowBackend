package com.dwalldorf.owbackend.service;

import com.dwalldorf.owbackend.BaseTest;
import java.time.Clock;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class StopWatchTest extends BaseTest {

    @Mock
    private Clock clock;

    private StopWatch stopWatch;

    @Override
    protected void afterSetup() {
        this.stopWatch = new StopWatch(clock);
    }

    @Test
    public void testStopWatch() throws Exception {
        final Long startRuntime = 1481545727680L;
        final Long expectedRuntime = 100L;

        Mockito.when(clock.millis()).thenReturn(startRuntime, startRuntime + expectedRuntime);

        stopWatch.start();
        stopWatch.stop();
        Long runtime = stopWatch.getRuntime();

        Assert.assertEquals(expectedRuntime, runtime);
    }
}