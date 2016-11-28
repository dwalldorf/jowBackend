package com.dwalldorf.owbackend.rest.controller;

import com.dwalldorf.owbackend.rest.dto.VersionDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/version")
public class VersionController {

    @Value("${app.version}")
    private String version;

    @GetMapping
    public VersionDto getVersion() {
        return new VersionDto(version);
    }
}
