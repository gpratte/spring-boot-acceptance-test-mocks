package com.example.message;

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
public class GenericStepDef extends SpringBootBaseIntegrationTest {

    LocalDate date;

    @Given("today")
    public void today() {
        // Do nothing
    }

    @When("get the date")
    public void get_the_date() {
        date = LocalDate.now();
    }

    @Then("the date is today")
    public void the_date_is_today() {
        Assert.assertEquals("Date should be the same", LocalDate.now(), date);
    }
}
