package com.dwalldorf.owbackend.config;

import java.time.Clock;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableAutoConfiguration
@EnableScheduling
public class AppConfiguration {

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
