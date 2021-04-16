package io.componenttesting.testmanager.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;


@Service
@EnableBinding(TestManagerSource.class)
public class EventPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventPublisher.class);

    private final MessageChannel output;

    public EventPublisher(MessageChannel output) {
        super();
        this.output = output;
    }

    public boolean publishMessageEvent(final String teamName, final String message) {
        LOGGER.info("sending message for team {}:  {}", teamName, message);

        return output.send(
                MessageBuilder.
                        withPayload(message).
                        setHeader("eventType", "CHECK_THIS").
                        setHeader("teamName", teamName).
                        build());
    }
}
