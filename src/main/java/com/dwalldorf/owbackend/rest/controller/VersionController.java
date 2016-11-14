package com.dwalldorf.owbackend.rest.controller;

import java.io.Serializable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/version")
public class VersionController {

    @Value("${app.version}")
    private String version;

    @RequestMapping
    public VersionDto getVersion() {
        return new VersionDto(version);
    }

    class VersionDto implements Serializable {

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
}
