package io.componenttesting.teammorale.event;

import io.componenttesting.teammorale.service.TeamMoraleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

@EnableBinding(TeamMoraleSink.class)
public class EventReceiver {

    public static final Logger LOGGER = LoggerFactory.getLogger(EventReceiver.class);

    @Autowired
    private TeamMoraleService teamMoraleService;

    @Autowired
    public EventReceiver() {

    }

    @StreamListener(value = TeamMoraleSink.APPLICATIONS_IN, condition = "headers['eventType'] =='TEAM_EVENT'")
    public void teamMoraleProcessor(@Payload String content, @Header(name = "teamName") String teamName) {
        LOGGER.info("message received for team {}: {}", teamName, content);
        try {
            teamMoraleService.handleNewHappening(teamName, content);
        } catch (Exception e) {
            LOGGER.error("something tragic has happened: {}",e);
        }
    }

}
