package io.componenttesting.testmanager.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.javacrumbs.jsonunit.core.Option;
import org.springframework.boot.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

public class ApiSteps {

    @LocalServerPort
    protected int port;

    protected Response response;

    @When("I use {string} to send:")
    public void postMessage(String path, String message) {
        response = baseRequest().body(message).contentType(ContentType.JSON).when().post(path);
    }

    @When("I delete {string}")
    public void delete(String path) {
        response = baseRequest().when().delete(path);
    }

    @Then("path {string} should exist and give me:")
    public void assertCallResponse(String path, String expectedBody) {
        response = baseRequest().when().get(path);
        assertThatJson(response.getBody().prettyPrint()).when(Option.IGNORING_ARRAY_ORDER).isEqualTo(expectedBody);
    }

    @Then("path {string} should receive a {int} status code")
    public void assertCallResponse(String path, int expectedStatus) {
        response = baseRequest().when().get(path);
        response.then().assertThat().statusCode(expectedStatus);
    }

    @Then("I should receive a {int} status code")
    public void assertStatusCode(int expectedStatus) {
        response.then().assertThat().statusCode(expectedStatus);
    }

    private RequestSpecification baseRequest() {
        return given().port(port);
    }
}
