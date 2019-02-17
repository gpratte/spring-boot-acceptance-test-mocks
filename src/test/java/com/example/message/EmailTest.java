package com.example.message;

import com.example.message.connector.ClockConnector;
import com.example.message.model.Email;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

/**
 * This class is ignored when running the JUnit tests.
 * The CucumberOneTest class is used to run the features when testing with JUnit.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class EmailTest {

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    ClockConnector clockConnector;

    // Start WireMock on some dynamic port
    // for some reason `dynamicPort()` is not working properly
//    @ClassRule
//    public static WireMockClassRule wiremock = new WireMockClassRule(
//        WireMockSpring.options().port(9999));

    @Test
    public void publish() {
//        wiremock.stubFor(get(urlEqualTo("/api/json/utc/now"))
//            .willReturn(aResponse().withHeader("Content-Type", "application/json").withBody("{\"$id\":\"123\",\"currentDateTime\":\"2019-01-01T21:09Z\",\"utcOffset\":\"00:00:00\",\"isDayLightSavingsTime\":false,\"dayOfTheWeek\":\"Friday\",\"timeZoneName\":\"UTC\",\"currentFileTime\":131948249734579777,\"ordinalDate\":\"2019-47\",\"serviceResponse\":null}")));
//
//        //clockConnector.getCurrent();
//
//        Email email = Email.builder()
//            .to("nobody@example.com")
//            .body("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque scelerisque, enim in congue consequat, ligula.")
//            .build();
//
//
//        jmsTemplate.convertAndSend("mailbox", email);

    }
}
