package io.componenttesting.testmanager.comp;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.componenttesting.testmanager.vo.Project;
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

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("event_test")
public class TestManagerRestTest extends AbstractEvent {

    @DisplayName("I should be able to create a new team")
    @Test
    public void createTeam() throws JsonProcessingException {
        Project project = new Project();
        project.setName("Fin");

        String body = objectMapper.writeValueAsString(project);
        given().port(port).body(body).contentType(ContentType.JSON).when().post("/api/v1/").then().statusCode(is(200));

        //good approach for debugging
        JsonPath response = given().port(port).when().get("/api/v1/Fin").getBody().jsonPath();
        Assertions.assertEquals("Fin", response.getString("name"));
    }

    @DisplayName("I should be able to delete an existing team")
    @Test
    public void deleteTeam() throws JsonProcessingException {
        Project project = new Project();
        project.setName("Fin201");
        String body = objectMapper.writeValueAsString(project);

        given().port(port).body(body).contentType(ContentType.JSON).when().post("/api/v1/").then().statusCode(is(200));

        given().port(port).when().delete("/api/v1/Fin201").then().statusCode(is(200));

        given().port(port).when().get("/api/v1/").then().assertThat().statusCode(200).body("name", not(hasItem(("Fin201"))));
    }

    @DisplayName("A team should always have a unique name")
    @ParameterizedTest(name = "{index}: name {0} should give status code {1}")
    @CsvSource({
            "Fin301, Fin301, 422",
            "FIN302, fin302, 422",
            "Fin303, Fin313, 200"
    })
    public void createTeamWithDuplicateName(final String existingTeamName, final String newTeamName, final int expectedStatusCode) throws JsonProcessingException {
        createNewProject(existingTeamName);

        Project project = new Project();
        project.setName(newTeamName);

        given().port(port).body(objectMapper.writeValueAsString(project)).contentType(ContentType.JSON).when().post("/api/v1/").then().statusCode(is(expectedStatusCode));
    }
}
