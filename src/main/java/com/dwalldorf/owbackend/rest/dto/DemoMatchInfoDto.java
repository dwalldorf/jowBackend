package com.dwalldorf.owbackend.rest.dto;

import com.dwalldorf.owbackend.model.CSGOMap;
import com.dwalldorf.owbackend.model.DemoMatchInfo;
import java.io.Serializable;
import java.util.Date;

public class DemoMatchInfoDto implements Serializable {

    private String matchId;

    private CSGOMap map;

    private String serverName;

    private String demoType;

    private Integer tickRate;

    private Date date;

    private Integer scoreTeam1;

    private Integer scoreTeam2;

    public static DemoMatchInfoDto fromEntity(final DemoMatchInfo entity) {
        if (entity == null) {
            return null;
        }

        return new DemoMatchInfoDto()
                .setMatchId(entity.getMatchId())
                .setMap(entity.getMap())
                .setServerName(entity.getServerName())
                .setDemoType(entity.getDemoType())
                .setTickRate(entity.getTickRate())
                .setDate(entity.getDate())
                .setScoreTeam1(entity.getScoreTeam1())
                .setScoreTeam2(entity.getScoreTeam2());
    }


    public String getMatchId() {
        return matchId;
    }

    public DemoMatchInfoDto setMatchId(String matchId) {
        this.matchId = matchId;
        return this;
    }

    public CSGOMap getMap() {
        return map;
    }

    public DemoMatchInfoDto setMap(CSGOMap map) {
        this.map = map;
        return this;
    }

    public String getServerName() {
        return serverName;
    }

    public DemoMatchInfoDto setServerName(String serverName) {
        this.serverName = serverName;
        return this;
    }

    public String getDemoType() {
        return demoType;
    }

    public DemoMatchInfoDto setDemoType(String demoType) {
        this.demoType = demoType;
        return this;
    }

    public Integer getTickRate() {
        return tickRate;
    }

    public DemoMatchInfoDto setTickRate(Integer tickRate) {
        this.tickRate = tickRate;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public DemoMatchInfoDto setDate(Date date) {
        this.date = date;
        return this;
    }

    public Integer getScoreTeam1() {
        return scoreTeam1;
    }

    public DemoMatchInfoDto setScoreTeam1(Integer scoreTeam1) {
        this.scoreTeam1 = scoreTeam1;
        return this;
    }

    public Integer getScoreTeam2() {
        return scoreTeam2;
    }

    public DemoMatchInfoDto setScoreTeam2(Integer scoreTeam2) {
        this.scoreTeam2 = scoreTeam2;
        return this;
    }
}
