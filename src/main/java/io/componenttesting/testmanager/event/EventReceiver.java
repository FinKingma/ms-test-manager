package io.componenttesting.testmanager.event;

import io.componenttesting.testmanager.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

@EnableBinding(TestManagerSink.class)
public class EventReceiver {

    public static final Logger LOGGER = LoggerFactory.getLogger(EventReceiver.class);

    @Autowired
    private ProjectService projectService;

    @Autowired
    public EventReceiver() {

    }

    @StreamListener(value = TestManagerSink.APPLICATIONS_IN, condition = "headers['eventType'] == T(io.componenttesting.model.EventHeader).TEST_EVENT.getValue()")
    public void teamMoraleProcessor(@Payload String content, @Header(name = "projectName") String projectName) {
        LOGGER.info("message received for team {}: {}", projectName, content);
        try {
            projectService.handleNewHappening(projectName, content);
        } catch (Exception e) {
            LOGGER.error("something tragic has happened: {}",e);
        }
    }

}
