package io.componenttesting.teammorale.event;

import io.componenttesting.teammorale.service.TeamMoraleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding(TeamMoraleSink.class)
public class EventReceiver {

    public static final Logger LOGGER = LoggerFactory.getLogger(EventReceiver.class);

    @Autowired
    private TeamMoraleService teamMoraleService;

    @Autowired
    public EventReceiver() {

    }

    @StreamListener(TeamMoraleSink.APPLICATIONS_IN)
    public void checkAndSortLoans(String content) {
        LOGGER.info("message received: {}");
        try {
            teamMoraleService.handleNewHappening(content);
        } catch (Exception e) {
            return;
        }
    }

    private static final <T> Message<T> message(T val) {
        return MessageBuilder.withPayload(val).build();
    }

}
