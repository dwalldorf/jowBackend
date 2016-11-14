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

    private boolean analyzed = false;

    @Embedded
    private DemoMatchInfo matchInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isAnalyzed() {
        return analyzed;
    }

    public void setAnalyzed(boolean analyzed) {
        this.analyzed = analyzed;
    }

    public DemoMatchInfo getMatchInfo() {
        return matchInfo;
    }

    public void setMatchInfo(DemoMatchInfo matchInfo) {
        this.matchInfo = matchInfo;
    }
}
