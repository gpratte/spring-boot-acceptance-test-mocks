package com.example.message.connector;

import com.example.message.model.Todo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConversionException;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.io.IOException;

public class TodoMessageConverter extends MappingJackson2MessageConverter {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public Object fromMessage(Message message) throws JMSException, MessageConversionException {

        Todo todo = null;
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            String json = textMessage.getText();
            try {
                return mapper.readValue(json, Todo.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (message instanceof BytesMessage) {
            BytesMessage bytesMessage = (BytesMessage) message;
            byte[] bytes = new byte[(int)bytesMessage.getBodyLength()];
            bytesMessage.readBytes(bytes);
            String json = new String(bytes);
            try {
                return mapper.readValue(json, Todo.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return todo;
    }

}
