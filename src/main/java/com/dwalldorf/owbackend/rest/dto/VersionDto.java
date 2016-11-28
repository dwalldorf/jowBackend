package com.dwalldorf.owbackend.rest.dto;

import java.io.Serializable;

public class VersionDto implements Serializable {

    private String version;

    public VersionDto(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
