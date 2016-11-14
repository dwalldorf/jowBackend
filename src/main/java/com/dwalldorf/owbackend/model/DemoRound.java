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

    public Integer getNr() {
        return nr;
    }

    public void setNr(Integer nr) {
        this.nr = nr;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Integer getWinner() {
        return winner;
    }

    public void setWinner(Integer winner) {
        this.winner = winner;
    }

    public Integer getReason() {
        return reason;
    }

    public void setReason(Integer reason) {
        this.reason = reason;
    }
}
