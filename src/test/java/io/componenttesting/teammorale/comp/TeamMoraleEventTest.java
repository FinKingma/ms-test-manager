package io.componenttesting.teammorale.comp;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.componenttesting.teammorale.dao.TeamsDao;
import io.componenttesting.teammorale.event.EventPublisher;
import io.componenttesting.teammorale.vo.Team;
import io.componenttesting.teammorale.vo.TeamEvent;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("event_test")
public class TeamMoraleEventTest extends AbstractEvent {

    @Autowired
    private TeamsDao teamsDao;

    @DisplayName("My teams happiness")
    @Test
    public void testStuff() throws JsonProcessingException {
        Team team = new Team();
        team.setVision("just stuff");
        team.setTeamName("Fin111");
        team.setHappiness(new BigDecimal(3));
        team.setMorale(new BigDecimal(3));
        given().port(port).body(objectMapper.writeValueAsString(team)).contentType(ContentType.JSON).when().post("/api/v1/").then().statusCode(is(200));

        TeamEvent event = new TeamEvent();
        event.setTeamName("Fin111");
        event.setFun(new BigDecimal(5));
        event.setLearned(new BigDecimal(5));

        sendInEvent("Fin111", objectMapper.writeValueAsString(event));
        List<JsonPath> output = getOutputJson();
        Assertions.assertEquals(output.size(), 1);

        JsonPath response = given().port(port).when().get("/api/v1/Fin111").getBody().jsonPath();
        MatcherAssert.assertThat(response.getDouble("happiness"), greaterThan(3.0));
    }
}
