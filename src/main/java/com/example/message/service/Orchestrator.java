package com.example.message.service;

import com.example.message.connector.ClockConnector;
import com.example.message.model.CurrentDateTime;
import com.example.message.model.Todo;
import org.springframework.stereotype.Service;

@Service
public class Orchestrator {

    private final ClockConnector clockConnector;

    public Orchestrator(ClockConnector clockConnector) {
        this.clockConnector = clockConnector;
    }

    /**
     * Orchestrate by getting the todo and then calling a rest endpoint.
     * @param todo
     */
    public void processTodo(Todo todo) {
        CurrentDateTime currentDateTime = clockConnector.getCurrent();
        System.out.println("Current Date Time " + currentDateTime);
    }
}
