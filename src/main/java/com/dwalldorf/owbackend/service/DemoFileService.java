package com.dwalldorf.owbackend.service;

import com.dwalldorf.owbackend.annotation.Log;
import com.dwalldorf.owbackend.exception.InvalidInputException;
import com.dwalldorf.owbackend.exception.LoginRequiredException;
import com.dwalldorf.owbackend.model.DemoFile;
import com.dwalldorf.owbackend.model.User;
import com.dwalldorf.owbackend.repository.DemoFileRepository;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DemoFileService {

    @Log
    private Logger logger;

    @Value("${app.demo.upload.dest}")
    private String uploadDirectory;

    private final DemoFileRepository demoFileRepository;

    private final UserService userService;

    @Inject
    public DemoFileService(DemoFileRepository demoFileRepository, UserService userService) {
        this.demoFileRepository = demoFileRepository;
        this.userService = userService;
    }

    public DemoFile findById(final String id) {
        return demoFileRepository.findOne(id);
    }

    public DemoFile save(DemoFile demoFile) throws LoginRequiredException {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            throw new LoginRequiredException();
        }
        demoFile.setUserId(currentUser.getId());

        return demoFileRepository.save(demoFile);
    }

    public DemoFile update(DemoFile demoFile) {
        return demoFileRepository.save(demoFile);
    }

    public DemoFile storeDemo(MultipartFile file) throws InvalidInputException {
        String filename = file.getOriginalFilename();

        if (!filename.endsWith(".dem")) {
            throw new InvalidInputException("Invalid file format");
        }

        DemoFile retVal = new DemoFile();

        try {
            File dest = Paths.get(uploadDirectory, filename).toFile();
            file.transferTo(dest);
            retVal.setFile(dest.getAbsolutePath());
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new InvalidInputException("Problem moving file", e);
        }

        return retVal;
    }
}
