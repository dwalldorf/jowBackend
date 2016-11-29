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

    public DemoMatchInfo setMap(CSGOMap map) {
        this.map = map;
        return this;
    }

    public DemoMatchInfo setMap(String mapName) {
        this.map = CSGOMap.valueOf(mapName);
        return this;
    }

    public String getServerName() {
        return serverName;
    }

    public DemoMatchInfo setServerName(String serverName) {
        this.serverName = serverName;
        return this;
    }

    public Integer getTickRate() {
        return tickRate;
    }

    public DemoMatchInfo setTickRate(Integer tickRate) {
        this.tickRate = tickRate;
        return this;
    }

    public long getDuration() {
        return duration;
    }

    public DemoMatchInfo setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    public DemoTeam getTeam1() {
        return team1;
    }

    public DemoMatchInfo setTeam1(DemoTeam team1) {
        this.team1 = team1;
        return this;
    }

    public DemoTeam getTeam2() {
        return team2;
    }

    public DemoMatchInfo setTeam2(DemoTeam team2) {
        this.team2 = team2;
        return this;
    }

    public List<DemoPlayer> getPlayers() {
        return players;
    }

    public DemoMatchInfo setPlayers(List<DemoPlayer> players) {
        this.players = players;
        return this;
    }

    public List<DemoRound> getRounds() {
        return rounds;
    }

    public DemoMatchInfo setRounds(List<DemoRound> rounds) {
        this.rounds = rounds;
        return this;
    }
}
