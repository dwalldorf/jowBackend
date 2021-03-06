package com.dwalldorf.owbackend.event.user;

import com.dwalldorf.owbackend.model.User;

public abstract class UserEvent {

    private final Action action;

    private final User user;

    public enum Action {
        LOGIN,
        LOGOUT,
        REGISTER,
        CREATED
    }

    public UserEvent(Action action, User user) {
        this.action = action;
        this.user = user;
    }

    public Action getAction() {
        return action;
    }

    public boolean hasUser() {
        return user != null;
    }

    public User getUser() {
        return user;
    }
}
