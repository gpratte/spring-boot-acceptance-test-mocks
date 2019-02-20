package com.example.message.one;

import com.example.message.SpringBootBaseIntegrationTest;
import com.example.message.model.Todo;
import com.example.message.repository.TodoRepository;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Autowired
    TodoRepository todoRepository;

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
        stubFor(get(urlEqualTo("/api/json/utc/now"))
            .willReturn(aResponse().withHeader("Content-Type", "application/json").withBody("{\"$id\":\"123\",\"currentDateTime\":\"2019-01-01T21:09Z\",\"utcOffset\":\"00:00:00\",\"isDayLightSavingsTime\":false,\"dayOfTheWeek\":\"Friday\",\"timeZoneName\":\"UTC\",\"currentFileTime\":131948249734579777,\"ordinalDate\":\"2019-47\",\"serviceResponse\":null}")));
        clockConnector.getCurrent();

        todo = Todo.builder()
            .description("Grocery shop")
            .priority(2)
            .done(false)
            .build();
    }

    @Given("todo walk dogs is composed")
    public void walkDogs() {
        stubFor(get(urlEqualTo("/api/json/utc/now"))
            .willReturn(aResponse().withHeader("Content-Type", "application/json").withBody("{\"$id\":\"123\",\"currentDateTime\":\"2019-12-12T21:09Z\",\"utcOffset\":\"00:00:00\",\"isDayLightSavingsTime\":false,\"dayOfTheWeek\":\"Friday\",\"timeZoneName\":\"UTC\",\"currentFileTime\":131948249734579777,\"ordinalDate\":\"2019-47\",\"serviceResponse\":null}")));

        clockConnector.getCurrent();

        todo = Todo.builder()
            .description("Walk dogs")
            .priority(2)
            .done(false)
            .build();
    }

    @When("the todo is published to the queue")
    public void publish() {
        sendTodo(todo);
    }

    @Then("the rest call should happen")
    public void verifyRestCalled() {
        // The message is asynchronous so give it some time to be delivered and acted upon.
        long start = System.currentTimeMillis();
        long now = System.currentTimeMillis();
        // wait 5 seconds (5000 millis) for the todo to be written to the database

        boolean found = false;

        sleeping:
        while (now - start < 5000l) {
            try {
                // Sleep for 1/2 a second
                Thread.sleep(500l);
            } catch (Exception e) {
                // do nothing
            }

            for (Todo todo : todoRepository.findAll()) {
                if (todo.getDescription().equals(this.todo.getDescription())) {
                    found = true;
                    break sleeping;
                }
            }

            now = System.currentTimeMillis();
        }

        Assert.assertTrue("should have found the todo", found);

        verify(2, getRequestedFor(urlEqualTo("/api/json/utc/now")));
    }
}
