package com.example.message;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * This class is ignored when running the JUnit tests.
 * The CucumberTest class is used to run the features when testing with JUnit.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Ignore
public class SpringBootBaseIntegrationTest {

    @LocalServerPort
    private int port;

}
