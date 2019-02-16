package com.example.message.connector;

import com.example.message.model.Email;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.io.IOException;

public class EmailMessageConverter implements MessageConverter {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
        return null;
    }

    @Override
    public Object fromMessage(Message message) throws JMSException, MessageConversionException {

        Email email = null;
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            String json = textMessage.getText();
            try {
                email = mapper.readValue(json, Email.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return email;
    }

}
