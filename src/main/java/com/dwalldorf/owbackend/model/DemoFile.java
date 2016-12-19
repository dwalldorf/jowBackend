package com.dwalldorf.owbackend.model;

import java.io.Serializable;
import org.mongodb.morphia.annotations.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class DemoFile implements Serializable {

    @Id
    private String id;

    private String userId;

    private String file;

    private Boolean queued = false;

    private Boolean processed = false;

    public String getId() {
        return id;
    }

    public DemoFile setId(String id) {
        this.id = id;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public DemoFile setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getFile() {
        return file;
    }

    public DemoFile setFile(String file) {
        this.file = file;
        return this;
    }

    public Boolean isQueued() {
        return queued;
    }

    public DemoFile setQueued() {
        setQueued(true);
        return this;
    }

    public DemoFile setQueued(Boolean queued) {
        this.queued = queued;
        return this;
    }

    public Boolean isProcessed() {
        return processed;
    }

    public DemoFile setProcessed() {
        setProcessed(true);
        return this;
    }

    public DemoFile setProcessed(Boolean processed) {
        this.processed = processed;
        return this;
    }
}
