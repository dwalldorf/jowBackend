package com.dwalldorf.owbackend.event.user;

import com.dwalldorf.owbackend.model.DemoFile;
import com.dwalldorf.owbackend.model.User;

public class UserUploadedDemoEvent extends UserEvent {

    private final User user;

    private final DemoFile demoFile;

    public UserUploadedDemoEvent(User user, DemoFile demoFile) {
        super(Action.CREATED, user);

        this.user = user;
        this.demoFile = demoFile;
    }

    public User getUser() {
        return user;
    }

    public DemoFile getDemoFile() {
        return demoFile;
    }
}
