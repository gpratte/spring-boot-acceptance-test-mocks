package com.example.message;

import com.example.message.model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.core.JmsTemplate;

@SpringBootApplication
public class MessageApplication implements CommandLineRunner {

    @Autowired
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) {
        SpringApplication.run(MessageApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        Email email = Email.builder()
//            .to("nobody@example.com")
//            .body("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque scelerisque, enim in congue consequat, ligula.")
//            .build();
//        System.out.println("Sending an email message.");
//        jmsTemplate.convertAndSend("mailbox", email);

    }
}

