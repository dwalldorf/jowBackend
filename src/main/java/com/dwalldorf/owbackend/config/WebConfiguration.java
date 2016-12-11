package com.dwalldorf.owbackend.config;

import com.dwalldorf.owbackend.annotation.Log;
import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Log
    private Logger logger;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");

        logger.info("globally enabled CORS");
    }
}
