package com.example.message.rest;

import com.example.message.SpringBootBaseIntegrationTest;
import com.example.message.message.AmqpReceiver;
import com.example.message.model.Todo;
import com.example.message.repository.TodoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * This class is ignored when running the JUnit tests.
 * The CucumberOneTest class is used to run the features when testing with JUnit.
 */
@Ignore
public class RestCreateTodoStepDef extends SpringBootBaseIntegrationTest {

    private Todo todo;
    private Long id;
    private long startMillis = Long.MAX_VALUE;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private AmqpReceiver amqpReceiver;

    @Given("todo paperword is composed")
    public void composeTodo() {
        todo = Todo.builder()
            .description("paperwork " + System.currentTimeMillis())
            .priority(2)
            .done(false)
            .build();

        startMillis = System.currentTimeMillis();
    }

    @When("the todos endpoint is called")
    public void callRest() throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        String todoAsJson = mapper.writeValueAsString(todo);
        HttpEntity<String> entity = new HttpEntity<>(todoAsJson, headers);

        TestRestTemplate restTemplate = new TestRestTemplate();
        id = restTemplate.postForObject("http://localhost:8080/api/todos", entity, Long.class);
    }

    @Then("the todo is persisted to the database")
    public void verifyTodoPersited() {
        Assert.assertNotNull("id should not be null", id);
        Assert.assertTrue("id millis should be greater than start millis", id > startMillis);

        // Check database
        Todo todoPersisted = todoRepository.findOne(id);
        Assert.assertEquals("should have found todo in H2 database", id, todoPersisted.getId());
        // The message is asynchronous so give it some time to be delivered and acted upon.
        long start = System.currentTimeMillis();
        long now = System.currentTimeMillis();
        // wait 3 seconds (3000 millis) for the todo show up in RabbitMQ
        while (now - start < 3000l) {
            try {
                // Sleep for 1/2 a second
                Thread.sleep(500l);
            } catch (Exception e) {
                // do nothing
            }

            System.out.println("!!! checking size " + AmqpReceiver.getMessages().size());
            if (amqpReceiver.getMessages().size() == 1) {
                break;
            }

            now = System.currentTimeMillis();
        }

        // Check message received
        Assert.assertEquals("should be one message received", 1, AmqpReceiver.getMessages().size());

        String message = AmqpReceiver.getMessages().get(0);
        Assert.assertTrue("description should match", message.indexOf(todo.getDescription()) > 0);
    }
}
