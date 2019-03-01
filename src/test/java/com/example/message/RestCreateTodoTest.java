package com.example.message;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;

/**
 * This class is a JUnit test but what it does is run the cucumber features.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features/rest-create-todo.feature",
    glue = "com.example.message.rest")
@ActiveProfiles("test")
public class RestCreateTodoTest {
}
