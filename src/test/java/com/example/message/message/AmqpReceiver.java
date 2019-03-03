package com.example.message.message;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AmqpReceiver {

    private List<String> messages = new ArrayList<>();

    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
        messages.add(message);
    }

    public List<String> getMessages() {
        return messages;
    }
}
