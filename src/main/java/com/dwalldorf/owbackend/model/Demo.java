package com.dwalldorf.owbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@JsonIgnoreProperties(ignoreUnknown = true)
public class Demo implements Serializable {

    @Id
    private String id;

    private String userId;

    private Boolean analyzed = false;

    @Embedded
    private DemoMatchInfo matchInfo;

    public String getId() {
        return id;
    }

    public Demo setId(String id) {
        this.id = id;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public Demo setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public Boolean isAnalyzed() {
        return analyzed;
    }

    public Demo setAnalyzed(Boolean analyzed) {
        this.analyzed = analyzed;
        return this;
    }

    public DemoMatchInfo getMatchInfo() {
        return matchInfo;
    }

    public Demo setMatchInfo(DemoMatchInfo matchInfo) {
        this.matchInfo = matchInfo;
        return this;
    }
}
