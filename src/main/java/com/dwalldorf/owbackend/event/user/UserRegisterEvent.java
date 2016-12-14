package com.dwalldorf.owbackend.event.user;

import com.dwalldorf.owbackend.model.User;

public class UserRegisterEvent extends UserEvent {

    public UserRegisterEvent(final User user) {
        super(Action.REGISTER, user);
    }
}
