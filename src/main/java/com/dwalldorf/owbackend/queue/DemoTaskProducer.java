package com.dwalldorf.owbackend.queue;

import com.dwalldorf.owbackend.Application;
import com.dwalldorf.owbackend.model.DemoFile;
import javax.inject.Inject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile(Application.PROFILE_INTEGRATION_TEST_EXCLUDE)
public class DemoTaskProducer {

    private final RabbitTemplate rabbitTemplate;

    @Inject
    public DemoTaskProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void queueDemo(DemoFile demoFile) {
        rabbitTemplate.convertAndSend("demo-upload", demoFile);
    }
}
