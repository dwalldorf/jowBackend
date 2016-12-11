package com.dwalldorf.owbackend.queue;

import com.dwalldorf.owbackend.annotation.Log;
import com.dwalldorf.owbackend.model.Demo;
import com.dwalldorf.owbackend.model.DemoFile;
import com.dwalldorf.owbackend.service.DemoFileService;
import com.dwalldorf.owbackend.service.DemoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class DemoDataReceiver implements MessageListener {

    @Log
    private Logger logger;

    private ObjectMapper objectMapper;

    private DemoService demoService;

    private DemoFileService demoFileService;

    @Inject
    public DemoDataReceiver(ObjectMapper objectMapper, DemoService demoService, DemoFileService demoFileService) {
        this.objectMapper = objectMapper;
        this.demoService = demoService;
        this.demoFileService = demoFileService;
    }

    @Override
    public void onMessage(Message message) {
        try {
            Demo demo = objectMapper.readValue(message.getBody(), Demo.class);

            // this is the demoFile id!
            String demoFileId = demo.getId();
            demo.setId(null);

            DemoFile demoFile = demoFileService.findById(demoFileId);
            if (demoFile == null) {
                logger.warn("Received demo from queue but couldn't find demo file. No user relation detectable, discarding demo");
                return;
            }

            demo.setUserId(demoFile.getUserId());
            demoService.save(demo);

            demoFile.setProcessed();
            demoFileService.update(demoFile);

            logger.info("received message from queue: \n {}", message);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
