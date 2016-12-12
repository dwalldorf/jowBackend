package com.dwalldorf.owbackend.service;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import java.time.Clock;
import javax.inject.Inject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(SCOPE_PROTOTYPE)
public class StopWatch {

    private final Clock clock;

    private Long start;
    private Long stop;
    private Long runtime = 0L;

    @Inject
    public StopWatch(Clock clock) {
        this.clock = clock;
    }

    private void reset() {
        this.start = 0L;
        this.stop = 0L;
        this.runtime = 0L;
    }

    public void start() {
        reset();
        this.start = clock.millis();
    }

    public Long stop() {
        this.stop = clock.millis();
        this.runtime = stop - start;

        return this.runtime;
    }

    /**
     * @return runtime in ms
     */
    public Long getRuntime() {
        return this.runtime;
    }
}
