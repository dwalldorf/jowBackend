package com.dwalldorf.owbackend.event.user;

import com.dwalldorf.owbackend.model.OverwatchVerdict;
import com.dwalldorf.owbackend.model.User;

public class UserPostedVerdictEvent {

    private final User user;

    private final OverwatchVerdict verdict;

    public UserPostedVerdictEvent(User user, OverwatchVerdict verdict) {
        this.user = user;
        this.verdict = verdict;
    }

    public User getUser() {
        return user;
    }

    public OverwatchVerdict getVerdict() {
        return verdict;
    }
}
