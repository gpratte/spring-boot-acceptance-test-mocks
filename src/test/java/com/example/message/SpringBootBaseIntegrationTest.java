package com.example.message;

import com.example.message.model.Email;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * This class is ignored when running the JUnit tests.
 * The CucumberTest class is used to run the features when testing with JUnit.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Ignore
public class SpringBootBaseIntegrationTest {

    @Autowired
    JmsTemplate jmsTemplate;

    @LocalServerPort
    private int port;

    protected void sendEmail(Email email) {
        System.out.println("!!! Sending an email message.");
        jmsTemplate.convertAndSend("mailbox", email);
    }

}
