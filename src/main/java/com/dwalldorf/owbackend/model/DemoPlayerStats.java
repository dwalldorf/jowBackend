package com.dwalldorf.owbackend.model;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class DemoPlayerStats {

    private Integer kills;

    private Integer assists;

    private Integer deaths;

    private Double kd;

    private Integer headshots;

    private Double headshotPercentage;

    private Integer teamKills;

    private Integer entryKills;

    private Integer bombPlants;

    private Integer bombDefuses;

    private Integer mvps;

    private Double killsPerRound;

    private Double assistsPerRound;

    private Double deathsPerRound;

    private Double averageDamagePerRound;

    private Integer kills1;

    private Integer kills2;

    private Integer kills3;

    private Integer kills4;

    private Integer kills5;

    private Integer vacRecords;

    private Integer overwatchRecords;

    public Integer getKills() {
        return kills;
    }

    public DemoPlayerStats setKills(Integer kills) {
        this.kills = kills;
        return this;
    }

    public Integer getAssists() {
        return assists;
    }

    public DemoPlayerStats setAssists(Integer assists) {
        this.assists = assists;
        return this;
    }

    public Integer getDeaths() {
        return deaths;
    }

    public DemoPlayerStats setDeaths(Integer deaths) {
        this.deaths = deaths;
        return this;
    }

    public Double getKd() {
        return kd;
    }

    public DemoPlayerStats setKd(Double kd) {
        this.kd = kd;
        return this;
    }

    public Integer getHeadshots() {
        return headshots;
    }

    public DemoPlayerStats setHeadshots(Integer headshots) {
        this.headshots = headshots;
        return this;
    }

    public Double getHeadshotPercentage() {
        return headshotPercentage;
    }

    public DemoPlayerStats setHeadshotPercentage(Double headshotPercentage) {
        this.headshotPercentage = headshotPercentage;
        return this;
    }

    public Integer getTeamKills() {
        return teamKills;
    }

    public DemoPlayerStats setTeamKills(Integer teamKills) {
        this.teamKills = teamKills;
        return this;
    }

    public Integer getEntryKills() {
        return entryKills;
    }

    public DemoPlayerStats setEntryKills(Integer entryKills) {
        this.entryKills = entryKills;
        return this;
    }

    public Integer getBombPlants() {
        return bombPlants;
    }

    public DemoPlayerStats setBombPlants(Integer bombPlants) {
        this.bombPlants = bombPlants;
        return this;
    }

    public Integer getBombDefuses() {
        return bombDefuses;
    }

    public DemoPlayerStats setBombDefuses(Integer bombDefuses) {
        this.bombDefuses = bombDefuses;
        return this;
    }

    public Integer getMvps() {
        return mvps;
    }

    public DemoPlayerStats setMvps(Integer mvps) {
        this.mvps = mvps;
        return this;
    }

    public Double getKillsPerRound() {
        return killsPerRound;
    }

    public DemoPlayerStats setKillsPerRound(Double killsPerRound) {
        this.killsPerRound = killsPerRound;
        return this;
    }

    public Double getAssistsPerRound() {
        return assistsPerRound;
    }

    public DemoPlayerStats setAssistsPerRound(Double assistsPerRound) {
        this.assistsPerRound = assistsPerRound;
        return this;
    }

    public Double getDeathsPerRound() {
        return deathsPerRound;
    }

    public DemoPlayerStats setDeathsPerRound(Double deathsPerRound) {
        this.deathsPerRound = deathsPerRound;
        return this;
    }

    public Double getAverageDamagePerRound() {
        return averageDamagePerRound;
    }

    public DemoPlayerStats setAverageDamagePerRound(Double averageDamagePerRound) {
        this.averageDamagePerRound = averageDamagePerRound;
        return this;
    }

    public Integer getKills1() {
        return kills1;
    }

    public DemoPlayerStats setKills1(Integer kills1) {
        this.kills1 = kills1;
        return this;
    }

    public Integer getKills2() {
        return kills2;
    }

    public DemoPlayerStats setKills2(Integer kills2) {
        this.kills2 = kills2;
        return this;
    }

    public Integer getKills3() {
        return kills3;
    }

    public DemoPlayerStats setKills3(Integer kills3) {
        this.kills3 = kills3;
        return this;
    }

    public Integer getKills4() {
        return kills4;
    }

    public DemoPlayerStats setKills4(Integer kills4) {
        this.kills4 = kills4;
        return this;
    }

    public Integer getKills5() {
        return kills5;
    }

    public DemoPlayerStats setKills5(Integer kills5) {
        this.kills5 = kills5;
        return this;
    }

    public Integer getVacRecords() {
        return vacRecords;
    }

    public DemoPlayerStats setVacRecords(Integer vacRecords) {
        this.vacRecords = vacRecords;
        return this;
    }

    public Integer getOverwatchRecords() {
        return overwatchRecords;
    }

    public DemoPlayerStats setOverwatchRecords(Integer overwatchRecords) {
        this.overwatchRecords = overwatchRecords;
        return this;
    }
}
