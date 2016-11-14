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

    public void setSteamId(String steamId) {
        this.steamId = steamId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
