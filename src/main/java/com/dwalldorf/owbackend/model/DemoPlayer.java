package com.dwalldorf.owbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.mongodb.morphia.annotations.Embedded;

@Embedded
@JsonIgnoreProperties(ignoreUnknown = true)
public class DemoPlayer implements Serializable {

    private String steamId;

    private Integer userId;

    private String name;

    public String getSteamId() {
        return steamId;
    }

    public DemoPlayer setSteamId(String steamId) {
        this.steamId = steamId;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public DemoPlayer setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public String getName() {
        return name;
    }

    public DemoPlayer setName(String name) {
        this.name = name;
        return this;
    }
}
