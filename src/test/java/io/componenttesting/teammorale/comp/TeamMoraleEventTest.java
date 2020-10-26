package io.componenttesting.teammorale.comp;

import io.componenttesting.teammorale.dao.TeamsDao;
import io.componenttesting.teammorale.event.EventPublisher;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("event_test")
public class TeamMoraleEventTest extends AbstractEvent {

    @Autowired
    private TeamsDao teamsDao;

    @Autowired
    private EventPublisher eventPublisher;

    @DisplayName("Just some test")
    @Test
    public void testStuff() {
        String body = getBody("test.json");
        sendInEvent(body);
        List<JsonPath> output = getOutputJson();

        Assertions.assertEquals(output.size(), 1);
    }
}
