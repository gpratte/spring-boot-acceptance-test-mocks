package com.example.message;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * This class is a JUnit test but what it does is run the cucumber features.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features/email2.feature",
    glue = "com.example.message.two")
public class CucumberTwoTest {
}
