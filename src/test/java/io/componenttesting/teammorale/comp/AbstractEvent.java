package io.componenttesting.teammorale.comp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.componenttesting.teammorale.event.TeamMoraleSink;
import io.componenttesting.teammorale.event.TeamMoraleSource;
import io.componenttesting.teammorale.service.TeamMoraleService;
import io.componenttesting.teammorale.vo.Team;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@ImportAutoConfiguration(org.springframework.cloud.stream.test.binder.TestSupportBinderAutoConfiguration.class)
public abstract class AbstractEvent {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractEvent.class);

    protected final ObjectMapper objectMapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    @Autowired
    private MessageCollector collector;

    @Autowired
    private TeamMoraleSource routingSource;

    @Autowired
    private TeamMoraleSink routingSink;

    @LocalServerPort
    protected int port;

    @BeforeEach
    public void before() {
        collector.forChannel(routingSource.output()).clear();
    }

    protected void createNewTeam(String teamName) {
        Team team = new Team();
        team.setHappiness(new BigDecimal(3));
        team.setMorale(new BigDecimal(3));
        team.setVision("stuff");
        team.setTeamName(teamName);

        String teamAsString = "";
        try {
            teamAsString = objectMapper.writeValueAsString(team);
        } catch (JsonProcessingException e) {
            LOGGER.error("could not turn object team into string");
        }

        given().port(port).body(teamAsString).contentType(ContentType.JSON).when().post("/api/v1/").then().statusCode(is(200));
    }

    protected Team newDefaultTeam(String teamName) {
        Team team = new Team();
        team.setVision("blaat");
        team.setTeamName(teamName);
        team.setHappiness(new BigDecimal(3));
        team.setMorale(new BigDecimal(3));
        return team;
    }

    protected void sendInEvent(String teamName, String content) {
        routingSink.sourceOfTeamMorale().send(MessageBuilder.
                withPayload(content).
                setHeader("eventType", "TEAM_EVENT").
                setHeader("teamName", teamName).
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
