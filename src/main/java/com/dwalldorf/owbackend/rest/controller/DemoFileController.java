package com.dwalldorf.owbackend.rest.controller;

import com.dwalldorf.owbackend.annotation.RequireLogin;
import com.dwalldorf.owbackend.event.user.UserUploadedDemoEvent;
import com.dwalldorf.owbackend.exception.InvalidInputException;
import com.dwalldorf.owbackend.exception.LoginRequiredException;
import com.dwalldorf.owbackend.model.DemoFile;
import com.dwalldorf.owbackend.queue.DemoTaskProducer;
import com.dwalldorf.owbackend.service.DemoFileService;
import com.dwalldorf.owbackend.service.UserService;
import javax.inject.Inject;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(DemoFileController.URI)
public class DemoFileController {

    public static final String URI = "/demofiles";

    private final ApplicationEventPublisher eventPublisher;

    private final DemoFileService demoFileService;

    private final DemoTaskProducer demoTaskProducer;

    private final UserService userService;

    @Inject
    public DemoFileController(
            ApplicationEventPublisher eventPublisher,
            DemoFileService demoFileService,
            DemoTaskProducer demoTaskProducer,
            UserService userService) {
        this.eventPublisher = eventPublisher;
        this.demoFileService = demoFileService;
        this.demoTaskProducer = demoTaskProducer;
        this.userService = userService;
    }

    @PostMapping
    @RequireLogin
    public ResponseEntity<DemoFile> uploadDemoFile(@RequestParam("file") MultipartFile file) throws InvalidInputException, LoginRequiredException {
        DemoFile demoFile = demoFileService.storeDemo(file);
        demoFile = demoFileService.save(demoFile);
        demoTaskProducer.queueDemo(demoFile);

        demoFile.setQueued();
        demoFile = demoFileService.update(demoFile);

        eventPublisher.publishEvent(new UserUploadedDemoEvent(userService.getCurrentUser(), demoFile));
        return new ResponseEntity<>(demoFile, HttpStatus.CREATED);
    }
}
