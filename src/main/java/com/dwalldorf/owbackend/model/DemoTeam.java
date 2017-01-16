package com.dwalldorf.owbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.ArrayList;
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

    public DemoTeam setTeamName(String teamName) {
        this.teamName = teamName;
        return this;
    }

    public Integer getTeamNumber() {
        return teamNumber;
    }

    public DemoTeam setTeamNumber(Integer teamNumber) {
        this.teamNumber = teamNumber;
        return this;
    }

    public List<DemoPlayer> getPlayers() {
        return players;
    }

    public DemoTeam setPlayers(List<DemoPlayer> players) {
        this.players = players;
        return this;
    }

    public DemoTeam addPlayer(DemoPlayer player) {
        if (this.players == null) {
            this.players = new ArrayList<>();
        }
        this.players.add(player);

        return this;
    }
}
