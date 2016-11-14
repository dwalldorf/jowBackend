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

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public boolean isQueued() {
        return queued;
    }

    public void setQueued() {
        setQueued(true);
    }

    public void setQueued(boolean queued) {
        this.queued = queued;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed() {
        setProcessed(true);
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }
}
