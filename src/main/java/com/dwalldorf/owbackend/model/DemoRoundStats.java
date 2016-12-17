package com.dwalldorf.owbackend.model;

import java.io.Serializable;
import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class DemoRoundStats implements Serializable {

    private Integer kills;

    private Integer kills1;

    private Integer kills2;

    private Integer kills3;

    private Integer kills4;

    private Integer kills5;

    private Boolean bombPlanted;

    private Boolean bombExploded;

    private Boolean bombDefused;

    private Integer startMoneyTeam1;

    private Integer startmoneyTeam2;

    private Integer equipmentValueTeam1;

    private Integer equipmentValueTeam2;

    public Integer getKills() {
        return kills;
    }

    public DemoRoundStats setKills(Integer kills) {
        this.kills = kills;
        return this;
    }

    public Integer getKills1() {
        return kills1;
    }

    public DemoRoundStats setKills1(Integer kills1) {
        this.kills1 = kills1;
        return this;
    }

    public Integer getKills2() {
        return kills2;
    }

    public DemoRoundStats setKills2(Integer kills2) {
        this.kills2 = kills2;
        return this;
    }

    public Integer getKills3() {
        return kills3;
    }

    public DemoRoundStats setKills3(Integer kills3) {
        this.kills3 = kills3;
        return this;
    }

    public Integer getKills4() {
        return kills4;
    }

    public DemoRoundStats setKills4(Integer kills4) {
        this.kills4 = kills4;
        return this;
    }

    public Integer getKills5() {
        return kills5;
    }

    public DemoRoundStats setKills5(Integer kills5) {
        this.kills5 = kills5;
        return this;
    }

    public Boolean isBombPlanted() {
        return bombPlanted;
    }

    public DemoRoundStats setBombPlanted(Boolean bombPlanted) {
        this.bombPlanted = bombPlanted;
        return this;
    }

    public Boolean isBombExploded() {
        return bombExploded;
    }

    public DemoRoundStats setBombExploded(Boolean bombExploded) {
        this.bombExploded = bombExploded;
        return this;
    }

    public Boolean isBombDefused() {
        return bombDefused;
    }

    public DemoRoundStats setBombDefused(Boolean bombDefused) {
        this.bombDefused = bombDefused;
        return this;
    }

    public Integer getStartMoneyTeam1() {
        return startMoneyTeam1;
    }

    public DemoRoundStats setStartMoneyTeam1(Integer startMoneyTeam1) {
        this.startMoneyTeam1 = startMoneyTeam1;
        return this;
    }

    public Integer getStartmoneyTeam2() {
        return startmoneyTeam2;
    }

    public DemoRoundStats setStartmoneyTeam2(Integer startmoneyTeam2) {
        this.startmoneyTeam2 = startmoneyTeam2;
        return this;
    }

    public Integer getEquipmentValueTeam1() {
        return equipmentValueTeam1;
    }

    public DemoRoundStats setEquipmentValueTeam1(Integer equipmentValueTeam1) {
        this.equipmentValueTeam1 = equipmentValueTeam1;
        return this;
    }

    public Integer getEquipmentValueTeam2() {
        return equipmentValueTeam2;
    }

    public DemoRoundStats setEquipmentValueTeam2(Integer equipmentValueTeam2) {
        this.equipmentValueTeam2 = equipmentValueTeam2;
        return this;
    }
}
