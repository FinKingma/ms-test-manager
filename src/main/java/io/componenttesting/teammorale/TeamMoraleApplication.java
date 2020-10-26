package io.componenttesting.teammorale;

import io.componenttesting.teammorale.event.TeamMoraleSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication(scanBasePackages = {"io.componenttesting.teammorale"})
public class TeamMoraleApplication {

    public static final Logger LOGGER = LoggerFactory.getLogger(TeamMoraleApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(TeamMoraleApplication.class, args);
        LOGGER.info("The team morale Application has started...");
    }
}

