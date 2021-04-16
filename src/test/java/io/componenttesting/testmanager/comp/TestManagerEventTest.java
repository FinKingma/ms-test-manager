package io.componenttesting.testmanager.comp;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.componenttesting.testmanager.dao.ProjectDao;
import io.componenttesting.testmanager.model.TestDataEntity;
import io.componenttesting.testmanager.vo.Project;
import io.componenttesting.testmanager.vo.TestDataEvent;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.closeTo;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("event_test")
public class TestManagerEventTest extends AbstractEvent {

    @Autowired
    private ProjectDao projectDao;

    @DisplayName("Event is ignored if the team does not exist")
    @Test
    public void testIgnoreIfTeamDoesntExist() throws JsonProcessingException {
        TestDataEvent event = new TestDataEvent();
        event.setTeamName("Fin011");
        event.setFun(new BigDecimal(3));
        event.setLearned(new BigDecimal(3));

        sendInEvent("Fin011", objectMapper.writeValueAsString(event));
        List<JsonPath> output = getOutputJson();
        Assertions.assertEquals(output.size(), 0);
    }
//
//    @DisplayName("My teams happiness and morale will grow by organizing fun and interesting events")
//    @ParameterizedTest(name = "{0}: base 3.0. event with fun({1}), learned({2}) will result in h({3}),m({4}) - {5}")
//    @CsvSource({
//            "1,5,5,4.33,4.33,increase all",
//            "2,1,1,1.67,1.67,decrease all",
//            "3,3,5,3,3.67,only increase morale through learning"
//    })
//    public void testImpactOnMorale(double index, BigDecimal eventFun, BigDecimal eventLearned, double expectedHappiness, double expectedMorale, String expanation) throws JsonProcessingException {
//        String teamName = "Fin11"+index;
//        Project project = new Project();
//        project.setVision("just stuff");
//        project.setTeamName(teamName);
//        project.setHappiness(new BigDecimal(3));
//        project.setMorale(new BigDecimal(3));
//        given().port(port).body(objectMapper.writeValueAsString(project)).contentType(ContentType.JSON).when().post("/api/v1/").then().statusCode(is(200));
//
//        TestDataEvent event = new TestDataEvent();
//        event.setTeamName(teamName);
//        event.setFun(eventFun);
//        event.setLearned(eventLearned);
//
//        sendInEvent(teamName, objectMapper.writeValueAsString(event));
//        List<JsonPath> output = getOutputJson();
//        Assertions.assertEquals(output.size(), 1);
//
//        JsonPath response = given().port(port).when().get("/api/v1/" + teamName).getBody().jsonPath();
//        MatcherAssert.assertThat(response.getDouble("happiness"), closeTo(expectedHappiness,0.03));
//        MatcherAssert.assertThat(response.getDouble("morale"), closeTo(expectedMorale, 0.03));
//    }
//
//    @DisplayName("After 6 months a new event will completely overwrite a teams morale")
//    @ParameterizedTest(name = "{index}: after {0} months the morale should be overwritten: {1}")
//    @CsvSource({
//            "7, true",
//            "6, true",
//            "5, false"
//
//    })
//    public void laggardOverwriteEvent(long months, boolean shouldBeOverwritten) throws JsonProcessingException {
//        TestDataEntity entity = new TestDataEntity();
//        entity.setNumberOfUpdates(1);
//        entity.setId(1L);
//        entity.setName("Fin211");
//        entity.setVision("just stuff");
//        entity.setMorale(new BigDecimal(1));
//        entity.setHappiness(new BigDecimal(1));
//        entity.setLastUpdatedOn(LocalDateTime.now().minusMonths(months));
//        entity.setCreatedOn(LocalDateTime.now().minusYears(1));
//        projectDao.save(entity);
//
//        TestDataEvent event = new TestDataEvent();
//        event.setTeamName("Fin211");
//        event.setFun(new BigDecimal(5));
//        event.setLearned(new BigDecimal(5));
//
//        sendInEvent("Fin211", objectMapper.writeValueAsString(event));
//
//        JsonPath response = given().port(port).when().get("/api/v1/Fin211").getBody().jsonPath();
//        if (shouldBeOverwritten) {
//            Assertions.assertEquals(response.getDouble("happiness"), 5.0);
//            Assertions.assertEquals(response.getDouble("morale"), 5.0);
//        } else {
//            MatcherAssert.assertThat(response.getDouble("happiness"), not(equalTo(5.0)));
//            MatcherAssert.assertThat(response.getDouble("morale"), not(equalTo(5.0)));
//        }
//
//    }
}
