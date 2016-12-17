package com.dwalldorf.owbackend.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class DemoMatchInfo implements Serializable {

    private String matchId;

    private CSGOMap map;

    private String serverName;

    private String demoType;

    private Integer tickRate;

    private Date date;

    private long duration;

    @Embedded
    private DemoTeam team1;

    @Embedded
    private DemoTeam team2;

    @Embedded
    private List<DemoRound> rounds;

    private Integer scoreHalftimeTeam1;

    private Integer scoreHalftimeTeam2;

    private Integer scoreTeam1;

    private Integer scoreTeam2;

    public String getMatchId() {
        return matchId;
    }

    public DemoMatchInfo setMatchId(String matchId) {
        this.matchId = matchId;
        return this;
    }

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

    public String getDemoType() {
        return demoType;
    }

    public DemoMatchInfo setDemoType(String demoType) {
        this.demoType = demoType;
        return this;
    }

    public Integer getTickRate() {
        return tickRate;
    }

    public DemoMatchInfo setTickRate(Integer tickRate) {
        this.tickRate = tickRate;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public DemoMatchInfo setDate(Date date) {
        this.date = date;
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

    public List<DemoRound> getRounds() {
        return rounds;
    }

    public DemoMatchInfo setRounds(List<DemoRound> rounds) {
        this.rounds = rounds;
        return this;
    }

    public Integer getScoreHalftimeTeam1() {
        return scoreHalftimeTeam1;
    }

    public DemoMatchInfo setScoreHalftimeTeam1(Integer scoreHalftimeTeam1) {
        this.scoreHalftimeTeam1 = scoreHalftimeTeam1;
        return this;
    }

    public Integer getScoreHalftimeTeam2() {
        return scoreHalftimeTeam2;
    }

    public DemoMatchInfo setScoreHalftimeTeam2(Integer scoreHalftimeTeam2) {
        this.scoreHalftimeTeam2 = scoreHalftimeTeam2;
        return this;
    }

    public Integer getScoreTeam1() {
        return scoreTeam1;
    }

    public DemoMatchInfo setScoreTeam1(Integer scoreTeam1) {
        this.scoreTeam1 = scoreTeam1;
        return this;
    }

    public Integer getScoreTeam2() {
        return scoreTeam2;
    }

    public DemoMatchInfo setScoreTeam2(Integer scoreTeam2) {
        this.scoreTeam2 = scoreTeam2;
        return this;
    }
}
