package com.example.message.connector;

import com.example.message.model.Todo;
import com.example.message.service.Orchestrator;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    private final Orchestrator orchestrator;

    public Receiver(Orchestrator orchestrator) {
        this.orchestrator = orchestrator;
    }

    @JmsListener(destination = "mailbox", containerFactory = "myFactory")
    public void receiveMessage(Todo todo) {
        System.out.println("Received " + todo);
        orchestrator.processTodo(todo);
    }
}
