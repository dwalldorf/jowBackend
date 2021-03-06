package com.dwalldorf.owbackend;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@SpringBootApplication
@Profile({"!integration-test"})
public class Application {

    public final static Marker appInfoMarker = MarkerFactory.getMarker("appInfo");

    public static final String PROFILE_DEV = "dev";
    public static final String PROFILE_INTEGRATION_TEST = "integration-test";
    public static final String PROFILE_INTEGRATION_TEST_EXCLUDE = "!" + PROFILE_INTEGRATION_TEST;

    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
    }
}
