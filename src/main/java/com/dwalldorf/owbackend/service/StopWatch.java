package com.dwalldorf.owbackend.service;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(SCOPE_PROTOTYPE)
public class StopWatch {

    private Long start;
    private Long stop;
    private Long runtime = 0L;

    private void reset() {
        this.start = 0L;
        this.stop = 0L;
        this.runtime = 0L;
    }

    public void start() {
        reset();
        this.start = System.currentTimeMillis();
    }

    public Long stop() {
        this.stop = System.currentTimeMillis();
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
