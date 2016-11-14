package com.dwalldorf.owbackend.model;

import java.io.Serializable;
import java.util.List;
import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class DemoMatchInfo implements Serializable {

    public enum Map {
        de_dust2("de_dust2"),
        DE_COBBLESTONE("de_cobblestone"),
        DE_CACHE("de_cache"),
        DE_OVERPASS("de_overpass"),
        de_mirage("de_mirage"),
        de_nuke("de_nuke"),
        DE_TRAIN("de_train"),

        DE_INFERNO("de_info"),
        DE_DUST("de_dust"),
        CS_ASSAULT("cs_assault"),
        CS_MILITIA("cs_militia"),
        CS_OFFICE("cs_office"),
        DE_VERTIGO("de_vertigo"),
        CS_ITALY("cs_italy"),
        DE_AZTEC("de_aztec");

        private final String name;

        Map(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private Map map;

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

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void setMap(String mapName) {
        this.map = Map.valueOf(mapName);
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
