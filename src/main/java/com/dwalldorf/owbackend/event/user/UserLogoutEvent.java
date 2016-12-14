package com.dwalldorf.owbackend.event.user;

import com.dwalldorf.owbackend.model.User;

public class UserLogoutEvent extends UserEvent {

    public UserLogoutEvent(User user) {
        super(Action.LOGOUT, user);
    }
}
