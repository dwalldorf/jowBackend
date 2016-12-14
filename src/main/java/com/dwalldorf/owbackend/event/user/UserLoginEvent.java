package com.dwalldorf.owbackend.event.user;

import com.dwalldorf.owbackend.model.User;

public class UserLoginEvent extends UserEvent {

    public UserLoginEvent(final User user) {
        super(Action.LOGIN, user);
    }
}
