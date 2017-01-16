package com.dwalldorf.owbackend.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class UserSettings implements Serializable {

    private List<String> followSteamIds = new ArrayList<>();

    private Boolean admin;

    public List<String> getFollowSteamIds() {
        return followSteamIds;
    }

    public UserSettings setFollowSteamIds(List<String> followSteamIds) {
        this.followSteamIds = followSteamIds;
        return this;
    }

    public Boolean isAdmin() {
        return admin;
    }

    public UserSettings setIsAdmin(Boolean isAdmin) {
        this.admin = isAdmin;
        return this;
    }
}
