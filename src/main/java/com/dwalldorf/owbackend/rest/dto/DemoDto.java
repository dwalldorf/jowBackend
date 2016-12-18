package com.dwalldorf.owbackend.rest.dto;

import com.dwalldorf.owbackend.model.Demo;
import java.io.Serializable;

public class DemoDto implements Serializable {

    private String id;

    private String userId;

    private boolean analyzed;

    private DemoMatchInfoDto matchInfo;

    public static DemoDto fromEntity(final Demo entity) {
        if (entity == null) {
            return null;
        }

        return new DemoDto()
                .setId(entity.getId())
                .setUserId(entity.getUserId())
                .setAnalyzed(entity.isAnalyzed())
                .setMatchInfo(DemoMatchInfoDto.fromEntity(entity.getMatchInfo()));
    }

    public String getId() {
        return id;
    }

    public DemoDto setId(String id) {
        this.id = id;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public DemoDto setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public boolean isAnalyzed() {
        return analyzed;
    }

    public DemoDto setAnalyzed(boolean analyzed) {
        this.analyzed = analyzed;
        return this;
    }

    public DemoMatchInfoDto getMatchInfo() {
        return matchInfo;
    }

    public DemoDto setMatchInfo(DemoMatchInfoDto matchInfo) {
        this.matchInfo = matchInfo;
        return this;
    }
}
