package com.example.message.connector;

import com.example.message.model.CurrentDateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ClockConnector {

    //private static final String URL = "http://worldclockapi.com/api/json/utc/now";
    private static final String URL = "http://localhost:9999/api/json/utc/now";

    private final RestTemplate restTemplate;

    public ClockConnector() {
        this.restTemplate = new RestTemplate();
    }

    public CurrentDateTime getCurrent() {
        CurrentDateTime currentDateTime =  restTemplate.getForObject(URL, CurrentDateTime.class);
        return currentDateTime;
    }
}
