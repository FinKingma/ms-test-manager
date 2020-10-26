package io.componenttesting.teammorale.comp;

import io.componenttesting.teammorale.event.TeamMoraleSink;
import io.componenttesting.teammorale.event.TeamMoraleSource;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

@ImportAutoConfiguration(org.springframework.cloud.stream.test.binder.TestSupportBinderAutoConfiguration.class)
public abstract class AbstractEvent {


    @Autowired
    private MessageCollector collector;

    @Autowired
    private TeamMoraleSource routingSource;

    @Autowired
    private TeamMoraleSink routingSink;

    @BeforeEach
    public void before() {
        collector.forChannel(routingSource.output()).clear();
    }

    protected void sendInEvent(String content) {
        routingSink.sourceOfTeamMorale().send(MessageBuilder.
                withPayload(content).
                setHeader("EventType", "INCOMING").
                build());
    }

    protected String getBody(final String fileName) {
        final String REQUEST_FILE_LOCATION = "src/test/resources/comp_test_json/";
        String fileContent = null;
        try {
            fileContent = FileUtils.readFileToString(new File(REQUEST_FILE_LOCATION + fileName), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }

    protected List<JsonPath> getOutputJson() {
        BlockingQueue<Message<?>> messages = collector.forChannel(routingSource.output());
        return messages.stream().map(m -> JsonPath.from(m.getPayload().toString())).collect(Collectors.toList());
    }
}
