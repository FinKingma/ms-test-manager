package io.componenttesting.testmanager.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.componenttesting.testmanager.event.TestManagerSink;
import io.componenttesting.testmanager.event.TestManagerSource;
import io.componenttesting.testmanager.event.TestDataEvent;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EventSteps {

    @Autowired
    private MessageCollector collector;

    @Autowired
    private TestManagerSource routingSource;

    @Autowired
    private TestManagerSink routingSink;

    @Before
    public void before() {
        collector.forChannel(routingSource.output()).clear();
    }

    @When("project {string} has received the following testdata:")
    public void sendTestData(String project, String testDataEvent) {
        sendInEvent(project, testDataEvent);
    }

    @When("project {string} has received {int} passing tests and {int} failing tests")
    public void sendTestDataList(String project, int passing, int failing) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        int iteration = 1;
        final TestDataEvent testData = new TestDataEvent();
        testData.setTestName("unit test A");
        testData.setProject(project);

        for (int i=0;i<passing;i++) {
            testData.setTestRunId(iteration++);
            testData.setResult("PASSED");
            sendInEvent(project, mapper.writeValueAsString(testData));
        }

        for (int i=0;i<failing;i++) {
            testData.setTestRunId(iteration++);
            testData.setResult("FAILED");
            sendInEvent(project, mapper.writeValueAsString(testData));
        }
    }

    @Then("the following message will be published {string}")
    public void checkPublish(String message) {
        List<String> payloads = getOutputJson();
        assertThat(payloads.size()).isEqualTo(1);
        String actualMessage = payloads.get(0);
        assertThat(actualMessage).isEqualTo(message);
    }

    @Then("no messages will be published")
    public void checkNoPublishes() {
        List<String> payloads = getOutputJson();
        assertThat(payloads.size()).isEqualTo(0);
    }

    private void sendInEvent(String teamName, String content) {
        routingSink.sourceOfTeamMorale().send(MessageBuilder.
                withPayload(content).
                setHeader("eventType", "TEAM_EVENT").
                setHeader("teamName", teamName).
                build());
    }

    protected List<String> getOutputJson() {
        BlockingQueue<Message<?>> messages = collector.forChannel(routingSource.output());
        return messages.stream().map(m -> m.getPayload().toString()).collect(Collectors.toList());
    }
}
