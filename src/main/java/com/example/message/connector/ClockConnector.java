package com.example.message.connector;

import com.example.message.model.CurrentDateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ClockConnector {

    private final RestTemplate restTemplate;
    private final String url;

    public ClockConnector(@Value("${restUrl}") String url) {
        this.restTemplate = new RestTemplate();
        this.url = url;
    }

    public CurrentDateTime getCurrent() {
        CurrentDateTime currentDateTime =  restTemplate.getForObject(url, CurrentDateTime.class);
        return currentDateTime;
    }
}
