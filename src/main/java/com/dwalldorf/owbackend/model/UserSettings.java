package com.dwalldorf.owbackend.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class UserSettings implements Serializable {

    private List<String> followSteamIds = new ArrayList<>();

    private boolean admin;

    public List<String> getFollowSteamIds() {
        return followSteamIds;
    }

    public UserSettings setFollowSteamIds(List<String> followSteamIds) {
        this.followSteamIds = followSteamIds;
        return this;
    }

    public boolean isAdmin() {
        return admin;
    }

    public UserSettings setIsAdmin(boolean isAdmin) {
        this.admin = isAdmin;
        return this;
    }
}
