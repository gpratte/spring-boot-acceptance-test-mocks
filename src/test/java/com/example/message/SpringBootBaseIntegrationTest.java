package com.example.message;

import com.example.message.connector.ClockConnector;
import com.example.message.model.Email;
import com.github.tomakehurst.wiremock.WireMockServer;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

/**
 * This class is ignored when running the JUnit tests.
 * The CucumberOneTest class is used to run the features when testing with JUnit.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Ignore
public class SpringBootBaseIntegrationTest {

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    protected ClockConnector clockConnector;

    // Configure wiremock server on port 9999
    protected WireMockServer wireMockServer = new WireMockServer(options().port(9999));

    protected void sendEmail(Email email) {
        jmsTemplate.convertAndSend("mailbox", email);
    }

}
