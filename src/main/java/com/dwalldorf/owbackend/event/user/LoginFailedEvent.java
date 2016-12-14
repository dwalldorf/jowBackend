package com.dwalldorf.owbackend.event.user;

public class LoginFailedEvent extends UserEvent {

    private final String loginName;

    public LoginFailedEvent(final String loginName) {
        super(Action.LOGIN, null);
        this.loginName = loginName;
    }

    public String getLoginName() {
        return loginName;
    }

    @Override
    public boolean hasUser() {
        return false;
    }
}
