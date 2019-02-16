package com.example.message;

import com.example.message.model.Email;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.junit.Ignore;

import java.time.LocalDate;

/**
 * This class is ignored when running the JUnit tests.
 * The CucumberTest class is used to run the features when testing with JUnit.
 */
@Ignore
public class EmailStepDef extends SpringBootBaseIntegrationTest {

    Email email;

    @Given("email is composed")
    public void compose() {
        email = Email.builder()
            .to("nobody@example.com")
            .body("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque scelerisque, enim in congue consequat, ligula.")
            .build();
    }

    @When("the email is published to the queue")
    public void publish() {
        this.sendEmail(email);
    }

    @Then("the log show the email was received")
    public void viewLog() {
        // Look at the log
    }
}
