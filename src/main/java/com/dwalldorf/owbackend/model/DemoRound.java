package com.dwalldorf.owbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.mongodb.morphia.annotations.Embedded;

@Embedded
@JsonIgnoreProperties(ignoreUnknown = true)
public class DemoRound implements Serializable {

    private Integer nr;

    private Long duration;

    private Integer winner;

    private Integer reason;

    private DemoRoundStats roundStats;

    public Integer getNr() {
        return nr;
    }

    public DemoRound setNr(Integer nr) {
        this.nr = nr;
        return this;
    }

    public Long getDuration() {
        return duration;
    }

    public DemoRound setDuration(Long duration) {
        this.duration = duration;
        return this;
    }

    public Integer getWinner() {
        return winner;
    }

    public DemoRound setWinner(Integer winner) {
        this.winner = winner;
        return this;
    }

    public Integer getReason() {
        return reason;
    }

    public DemoRound setReason(Integer reason) {
        this.reason = reason;
        return this;
    }

    public DemoRoundStats getRoundStats() {
        return roundStats;
    }

    public DemoRound setRoundStats(DemoRoundStats roundStats) {
        this.roundStats = roundStats;
        return this;
    }
}
