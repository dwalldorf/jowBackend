package com.dwalldorf.owbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.List;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Reference;

@Embedded
@JsonIgnoreProperties(ignoreUnknown = true)
public class DemoTeam implements Serializable {

    private String teamName;

    private Integer teamNumber;

    @Reference
    private List<DemoPlayer> players;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getTeamNumber() {
        return teamNumber;
    }

    public void setTeamNumber(Integer teamNumber) {
        this.teamNumber = teamNumber;
    }

    public List<DemoPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(List<DemoPlayer> players) {
        this.players = players;
    }
}
