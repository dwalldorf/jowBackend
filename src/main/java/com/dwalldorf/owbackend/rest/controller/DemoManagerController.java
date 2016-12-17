package com.dwalldorf.owbackend.rest.controller;

import com.dwalldorf.owbackend.annotation.RequireLogin;
import com.dwalldorf.owbackend.model.Demo;
import com.dwalldorf.owbackend.service.CsgoDemosManagerService;
import javax.inject.Inject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(DemoManagerController.URI)
public class DemoManagerController {

    public static final String URI = DemoController.URI + "/demos_manager";

    private final CsgoDemosManagerService demosManagerService;

    @Inject
    public DemoManagerController(CsgoDemosManagerService demosManagerService) {
        this.demosManagerService = demosManagerService;
    }

    @PostMapping
    @RequireLogin
    public ResponseEntity<Demo> postExcel(@RequestParam("file") MultipartFile file) {
        demosManagerService.processFile(file);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
