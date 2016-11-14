package com.dwalldorf.owbackend.config;

import com.dwalldorf.owbackend.queue.DemoDataReceiver;
import javax.inject.Inject;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqDemoInfoConsumerConfiguration extends RabbitmqConfiguration {

    @Value("${amqp.queues.analyze}")
    private String queue;

    @Inject
    private DemoDataReceiver demoDataReceiver;

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        template.setRoutingKey(queue);
        template.setQueue(queue);
        template.setMessageConverter(jsonMessageConverter());

        return template;
    }

    @Bean
    public Queue demoResultQueue() {
        return new Queue(queue);
    }

    @Bean
    public SimpleMessageListenerContainer listenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setQueueNames(queue);
        container.setMessageListener(messageListenerAdapter());
        return container;
    }

    @Bean
    public MessageListenerAdapter messageListenerAdapter() {
        return new MessageListenerAdapter(demoDataReceiver, jsonMessageConverter());
    }
}
