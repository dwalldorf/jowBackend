package com.dwalldorf.owbackend.service;

import com.dwalldorf.owbackend.model.DemoFile;
import com.dwalldorf.owbackend.repository.DemoFileRepository;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

@Service
public class DemoUploadService {

    @Inject
    private DemoFileRepository demoFileRepository;

    public DemoFile store(DemoFile demoFile) {
        return null;
    }
}
