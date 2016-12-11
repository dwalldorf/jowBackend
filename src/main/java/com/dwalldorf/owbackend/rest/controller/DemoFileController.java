package com.dwalldorf.owbackend.rest.controller;

import com.dwalldorf.owbackend.annotation.RequireLogin;
import com.dwalldorf.owbackend.exception.InvalidInputException;
import com.dwalldorf.owbackend.exception.LoginRequiredException;
import com.dwalldorf.owbackend.model.DemoFile;
import com.dwalldorf.owbackend.queue.DemoTaskProducer;
import com.dwalldorf.owbackend.service.DemoFileService;
import javax.inject.Inject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/demofiles")
public class DemoFileController {

    private DemoFileService demoFileService;

    private DemoTaskProducer demoTaskProducer;

    @Inject
    public DemoFileController(DemoFileService demoFileService, DemoTaskProducer demoTaskProducer) {
        this.demoFileService = demoFileService;
        this.demoTaskProducer = demoTaskProducer;
    }

    @PostMapping
    @RequireLogin
    public ResponseEntity<DemoFile> uploadDemoFile(@RequestParam("file") MultipartFile file) throws InvalidInputException, LoginRequiredException {
        DemoFile demoFile = demoFileService.storeDemo(file);
        demoFile = demoFileService.save(demoFile);
        demoTaskProducer.queueDemo(demoFile);

        demoFile.setQueued();
        demoFile = demoFileService.update(demoFile);

        return new ResponseEntity<>(demoFile, HttpStatus.CREATED);
    }
}
