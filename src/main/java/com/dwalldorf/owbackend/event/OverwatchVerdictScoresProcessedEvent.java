package com.dwalldorf.owbackend.event;

import com.dwalldorf.owbackend.model.OverwatchUserScore;
import com.dwalldorf.owbackend.model.OverwatchUserScore.Period;

public class OverwatchVerdictScoresProcessedEvent {

    private final boolean isInitialRun;

    private final Period period;

    private final int processedScores;

    private final long runtime;

    public OverwatchVerdictScoresProcessedEvent(boolean isInitialRun, Period period, int processedScores, long runtime) {

        this.isInitialRun = isInitialRun;
        this.period = period;
        this.processedScores = processedScores;
        this.runtime = runtime;
    }

    public static class Builder {
        private boolean isInitialRun = false;
        private OverwatchUserScore.Period period;
        private int processedScores;
        private long runtime;

        public Builder setIsInitialRun() {
            this.isInitialRun = true;
            return this;
        }

        public Builder setPeriod(OverwatchUserScore.Period period) {
            this.period = period;
            return this;
        }

        public Builder setProcessedScores(int processedScores) {
            this.processedScores = processedScores;
            return this;
        }

        public Builder setRuntime(long runtime) {
            this.runtime = runtime;
            return this;
        }

        public OverwatchVerdictScoresProcessedEvent build() {
            return new OverwatchVerdictScoresProcessedEvent(isInitialRun, period, processedScores, runtime);
        }
    }

    public static Builder builder() {
        return new OverwatchVerdictScoresProcessedEvent.Builder();
    }

    public boolean isInitialRun() {
        return isInitialRun;
    }

    public Period getPeriod() {
        return period;
    }

    public int getProcessedScores() {
        return processedScores;
    }

    public long getRuntime() {
        return runtime;
    }
}
