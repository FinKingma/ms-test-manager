package io.componenttesting.testmanager.comp;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.componenttesting.testmanager.dao.ProjectDao;
import io.componenttesting.testmanager.model.ProjectEntity;
import io.componenttesting.testmanager.vo.TestDataEvent;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.closeTo;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("event_test")
public class TestManagerEventTest extends AbstractEvent {

    @Autowired
    private ProjectDao projectDao;

    @DisplayName("When test data is received of an existing project it will be added")
    @Test
    public void addTestDataToProjectOnceReceived() throws JsonProcessingException {
        String projectName = "Fins project 01";

        createNewProject(projectName);

        TestDataEvent event = new TestDataEvent();
        event.setProject(projectName);
        event.setTestName("test01");
        event.setTestRunId(12);
        event.setResult("PASSED");

        sendInEvent(projectName, objectMapper.writeValueAsString(event));

        ProjectEntity project = projectDao.findByNameIgnoreCase(projectName).get();

        Assertions.assertEquals(1, project.getTestdata().size());

        List<JsonPath> output = getOutputJson();
        Assertions.assertEquals(output.size(), 1);
    }

    @DisplayName("Event is ignored if the team does not exist")
    @Test
    public void testIgnoreIfTeamDoesntExist() throws JsonProcessingException {
        String projectName = "Fins project 02";

        TestDataEvent event = new TestDataEvent();
        event.setProject(projectName);
        event.setTestName("test01");
        event.setTestRunId(12);
        event.setResult("PASSED");

        sendInEvent(projectName, objectMapper.writeValueAsString(event));

        List<JsonPath> output = getOutputJson();
        Assertions.assertEquals(output.size(), 0);
    }
}
