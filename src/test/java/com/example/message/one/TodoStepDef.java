package com.example.message.one;

import com.example.message.SpringBootBaseIntegrationTest;
import com.example.message.model.Todo;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Ignore;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;

/**
 * This class is ignored when running the JUnit tests.
 * The CucumberOneTest class is used to run the features when testing with JUnit.
 */
@Ignore
public class TodoStepDef extends SpringBootBaseIntegrationTest {

    private Todo todo;

    @Before
    public void before() {
        wireMockServer.start();
        configureFor("localhost", wireMockServer.port());

    }

    @After
    public void after() {
        wireMockServer.stop();
    }

    @Given("todo grocery shop is composed")
    public void groceryShop() {
        todo = Todo.builder()
            .description("Grocery shop")
            .priority(2)
            .done(false)
            .build();

        stubFor(get(urlEqualTo("/api/json/utc/now"))
            .willReturn(aResponse().withHeader("Content-Type", "application/json").withBody("{\"$id\":\"123\",\"currentDateTime\":\"2019-01-01T21:09Z\",\"utcOffset\":\"00:00:00\",\"isDayLightSavingsTime\":false,\"dayOfTheWeek\":\"Friday\",\"timeZoneName\":\"UTC\",\"currentFileTime\":131948249734579777,\"ordinalDate\":\"2019-47\",\"serviceResponse\":null}")));

        clockConnector.getCurrent();

    }

    @Given("todo walk dogs is composed")
    public void walkDogs() {
        todo = Todo.builder()
            .description("Walk dogs")
            .priority(2)
            .done(false)
            .build();

        stubFor(get(urlEqualTo("/api/json/utc/now"))
            .willReturn(aResponse().withHeader("Content-Type", "application/json").withBody("{\"$id\":\"123\",\"currentDateTime\":\"2019-12-12T21:09Z\",\"utcOffset\":\"00:00:00\",\"isDayLightSavingsTime\":false,\"dayOfTheWeek\":\"Friday\",\"timeZoneName\":\"UTC\",\"currentFileTime\":131948249734579777,\"ordinalDate\":\"2019-47\",\"serviceResponse\":null}")));

        clockConnector.getCurrent();

    }

    @When("the todo is published to the queue")
    public void publish() {
        this.sendTodo(todo);
    }

    @Then("the rest call should happen")
    public void verifyRestCalled() {
        verify(2, getRequestedFor(urlEqualTo("/api/json/utc/now")));
    }
}
