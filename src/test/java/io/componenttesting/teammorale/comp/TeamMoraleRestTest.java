package io.componenttesting.teammorale.comp;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.componenttesting.teammorale.vo.Team;
import io.componenttesting.teammorale.vo.TeamEvent;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("event_test")
public class TeamMoraleRestTest extends AbstractEvent {

    @DisplayName("I should be able to create a new team")
    @Test
    public void createTeam() {
        String newTeam = getBody("newTeam.json");

        given().port(port).body(newTeam).contentType(ContentType.JSON).when().post("/api/v1/").then().statusCode(is(200));

        //good approach for debugging
        JsonPath response = given().port(port).when().get("/api/v1/").getBody().jsonPath();
        Assertions.assertEquals("Fin", response.getString("name[0]"));
    }

    @DisplayName("I should be able to update an existing team")
    @Test
    public void updateTeam() throws JsonProcessingException {
        Team team = newDefaultTeam("Fin101");

        given().port(port).body(objectMapper.writeValueAsString(team)).contentType(ContentType.JSON).when().post("/api/v1/").then().statusCode(is(200));

        team.setVision("Something great");
        given().port(port).body(objectMapper.writeValueAsString(team)).contentType(ContentType.JSON).when().put("/api/v1/").then().statusCode(is(200));

        //cleaner approach
        given().port(port).when().get("/api/v1/").then().assertThat().statusCode(200).body("vision[0]", equalTo("Something great"));
    }

    @DisplayName("I should be able to delete an existing team")
    @Test
    public void deleteTeam() throws JsonProcessingException {
        Team team = newDefaultTeam("Fin201");

        given().port(port).body(objectMapper.writeValueAsString(team)).contentType(ContentType.JSON).when().post("/api/v1/").then().statusCode(is(200));

        given().port(port).when().delete("/api/v1/Fin201").then().statusCode(is(200));

        given().port(port).when().get("/api/v1/").then().assertThat().statusCode(200).body("name", not(hasItem(("Fin201"))));
    }

    @DisplayName("A team should always have a unique name")
    @ParameterizedTest(name = "{index}: name {0} should give status code {1}")
    @CsvSource({
            "Fin123, Fin123, 422",
            "FIN124, fin124, 422",
            "Fin125, Fin126, 200"
    })
    public void createTeamWithDuplicateName(final String existingTeamName, final String newTeamName, final int expectedStatusCode) throws JsonProcessingException {
        createNewTeam(existingTeamName);

        Team team = new Team();
        team.setTeamName(newTeamName);
        team.setVision(newTeamName);
        team.setMorale(new BigDecimal(3));
        team.setHappiness(new BigDecimal(3));

        given().port(port).body(objectMapper.writeValueAsString(team)).contentType(ContentType.JSON).when().post("/api/v1/").then().statusCode(is(expectedStatusCode));
    }
}
