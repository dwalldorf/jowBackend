package com.dwalldorf.owbackend.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"default", "dev"})
public class RabbitmqDemoFileProducerConfiguration extends RabbitmqConfiguration {

    @Value("${amqp.queues.parse}")
    private String parseQueue;

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(parseQueue);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with(parseQueue);
    }

    @Bean
    public Queue queue() {
        return new Queue(parseQueue);
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        template.setRoutingKey(parseQueue);
        template.setQueue(parseQueue);
        template.setMessageConverter(jsonMessageConverter());

        return template;
    }
}
