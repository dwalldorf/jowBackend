package com.dwalldorf.owbackend.service;

import com.dwalldorf.owbackend.exception.InvalidInputException;
import com.dwalldorf.owbackend.model.DemoFile;
import com.dwalldorf.owbackend.model.User;
import com.dwalldorf.owbackend.repository.DemoFileRepository;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DemoFileService {

    private final static Logger logger = LoggerFactory.getLogger(DemoFileService.class);

    @Value("${app.demo.upload.dest}")
    private String uploadDirectory;

    @Inject
    private DemoFileRepository demoFileRepository;

    @Inject
    private UserService userService;

    public DemoFile findById(final String id) {
        return demoFileRepository.findOne(id);
    }

    public DemoFile persist(DemoFile demoFile) {
        User currentUser = userService.getCurrentUser();
        if (currentUser != null) {
            demoFile.setUserId(currentUser.getId());
        }

        DemoFile savedDemoFile = demoFileRepository.save(demoFile);
        if (currentUser == null) {
            logger.warn("Persisting demofile without knowing current user. DemoFile id: {}", savedDemoFile.getId());
        }
        return savedDemoFile;
    }

    public DemoFile save(DemoFile demoFile) {
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
