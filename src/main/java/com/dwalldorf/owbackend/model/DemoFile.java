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

    private boolean queued = false;

    private boolean processed = false;

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

    public boolean isQueued() {
        return queued;
    }

    public DemoFile setQueued() {
        setQueued(true);
        return this;
    }

    public DemoFile setQueued(boolean queued) {
        this.queued = queued;
        return this;
    }

    public boolean isProcessed() {
        return processed;
    }

    public DemoFile setProcessed() {
        setProcessed(true);
        return this;
    }

    public DemoFile setProcessed(boolean processed) {
        this.processed = processed;
        return this;
    }
}
