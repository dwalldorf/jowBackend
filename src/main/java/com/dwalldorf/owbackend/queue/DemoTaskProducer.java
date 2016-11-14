package com.dwalldorf.owbackend.queue;

import com.dwalldorf.owbackend.model.DemoFile;
import javax.inject.Inject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

@Component
public class DemoTaskProducer {

    @Inject
    private RabbitTemplate rabbitTemplate;

    @Inject
    private MessageConverter jsonMessageConverter;

    public void queueDemo(DemoFile demoFile) {
        rabbitTemplate.convertAndSend("demo-upload", demoFile);
    }
}
