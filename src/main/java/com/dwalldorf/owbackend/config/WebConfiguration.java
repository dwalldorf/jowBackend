package com.dwalldorf.owbackend.config;

import com.dwalldorf.owbackend.Application;
import com.dwalldorf.owbackend.annotation.Log;
import java.util.Arrays;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Log
    private Logger logger;

    private final Environment environment;

    @Inject
    public WebConfiguration(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // for dev profile only
        if (Arrays.asList(environment.getActiveProfiles()).contains(Application.PROFILE_DEV)) {
            registry.addMapping("/**");
            logger.info("globally enabled CORS");
        }
    }
}
