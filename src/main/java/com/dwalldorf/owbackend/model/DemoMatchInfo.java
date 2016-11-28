package com.dwalldorf.owbackend.model;

import java.io.Serializable;
import java.util.List;
import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class DemoMatchInfo implements Serializable {

    private CSGOMap map;

    private String serverName;

    private Integer tickRate;

    private long duration;

    @Embedded
    private DemoTeam team1;

    @Embedded
    private DemoTeam team2;

    @Embedded
    private List<DemoPlayer> players;

    @Embedded
    private List<DemoRound> rounds;

    public CSGOMap getMap() {
        return map;
    }

    public void setMap(CSGOMap map) {
        this.map = map;
    }

    public void setMap(String mapName) {
        this.map = CSGOMap.valueOf(mapName);
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public Integer getTickRate() {
        return tickRate;
    }

    public void setTickRate(Integer tickRate) {
        this.tickRate = tickRate;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public DemoTeam getTeam1() {
        return team1;
    }

    public void setTeam1(DemoTeam team1) {
        this.team1 = team1;
    }

    public DemoTeam getTeam2() {
        return team2;
    }

    public void setTeam2(DemoTeam team2) {
        this.team2 = team2;
    }

    public List<DemoPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(List<DemoPlayer> players) {
        this.players = players;
    }

    public List<DemoRound> getRounds() {
        return rounds;
    }

    public void setRounds(List<DemoRound> rounds) {
        this.rounds = rounds;
    }
}
