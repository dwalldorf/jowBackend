package com.dwalldorf.owbackend.model;

import java.io.Serializable;
import java.util.Date;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = OverwatchVerdict.COLLECTION_NAME)
public class OverwatchVerdict implements Serializable {

    public final static String COLLECTION_NAME = "verdicts";

    private String id;

    private String userId;

    private Date creationDate;

    private Date overwatchDate;

    private CSGOMap map;

    private boolean aimAssist = false;

    private boolean visionAssist = false;

    private boolean otherAssist = false;

    private boolean griefing = false;

    public String getId() {
        return id;
    }

    public OverwatchVerdict setId(String id) {
        this.id = id;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public OverwatchVerdict setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public OverwatchVerdict setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public Date getOverwatchDate() {
        return overwatchDate;
    }

    public OverwatchVerdict setOverwatchDate(Date overwatchDate) {
        this.overwatchDate = overwatchDate;
        return this;
    }

    public CSGOMap getMap() {
        return map;
    }

    public OverwatchVerdict setMap(CSGOMap map) {
        this.map = map;
        return this;
    }

    public boolean isAimAssist() {
        return aimAssist;
    }

    public OverwatchVerdict setAimAssist(boolean aimAssist) {
        this.aimAssist = aimAssist;
        return this;
    }

    public boolean isVisionAssist() {
        return visionAssist;
    }

    public OverwatchVerdict setVisionAssist(boolean visionAssist) {
        this.visionAssist = visionAssist;
        return this;
    }

    public boolean isOtherAssist() {
        return otherAssist;
    }

    public OverwatchVerdict setOtherAssist(boolean otherAssist) {
        this.otherAssist = otherAssist;
        return this;
    }

    public boolean isGriefing() {
        return griefing;
    }

    public OverwatchVerdict setGriefing(boolean griefing) {
        this.griefing = griefing;
        return this;
    }
}
