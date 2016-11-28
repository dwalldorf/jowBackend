package com.dwalldorf.owbackend.it;

import com.dwalldorf.owbackend.config.MongoConfiguration;
import com.dwalldorf.owbackend.config.RabbitmqConfiguration;
import com.dwalldorf.owbackend.config.RabbitmqDemoFileProducerConfiguration;
import com.dwalldorf.owbackend.config.RabbitmqDemoInfoConsumerConfiguration;
import com.dwalldorf.owbackend.config.SessionRepositoryConfiguration;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import javax.servlet.http.HttpSession;
import org.mockito.Mockito;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.session.SessionAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.mock.web.MockHttpSession;

@Configuration
@SpringBootApplication(exclude = {
        MongoConfiguration.class,
        SessionRepositoryConfiguration.class,
        RabbitmqConfiguration.class,
        RabbitmqDemoFileProducerConfiguration.class,
        RabbitmqDemoInfoConsumerConfiguration.class,
        MongoAutoConfiguration.class,
        SessionAutoConfiguration.class,
        RedisAutoConfiguration.class,
        RedisRepositoriesAutoConfiguration.class
})
@PropertySource("classpath:application.properties")
@Profile(TestConfiguration.INTEGRATION_TEST_PROFILE)
public class TestConfiguration {

    public final static String INTEGRATION_TEST_PROFILE = "integration-test";

    @Bean
    @Primary
    public HttpSession httpSession() {
        return new MockHttpSession();
    }

    @Bean
    @Primary
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory factoryMock = Mockito.mock(JedisConnectionFactory.class);
        JedisConnection connection = Mockito.mock(JedisConnection.class);
        Mockito.when(factoryMock.getConnection()).thenReturn(connection);

        return factoryMock;
    }

    @Bean
    @Primary
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    @Primary
    public Mongo mongo() throws Exception {
        return Mockito.mock(MongoClient.class);
    }

    @Bean
    @Primary
    public MongoClient mongoClient() throws Exception {
        return (MongoClient) mongo();
    }
}
