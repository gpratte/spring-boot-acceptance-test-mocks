package com.example.message.service;

import com.example.message.model.Todo;
import com.example.message.repository.TodoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final RabbitTemplate rabbitTemplate;
    private final String rabbitExchange;
    private final String rabbitRoutingKey;
    private ObjectMapper objectMapper = new ObjectMapper();

    public TodoService(TodoRepository todoRepository, RabbitTemplate rabbitTemplate, @Value("${rabbitmq.exchange}") String rabbitExchange, @Value("${rabbitmq.routing.key}") String rabbitRoutingKey) {
        this.todoRepository = todoRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitExchange = rabbitExchange;
        this.rabbitRoutingKey = rabbitRoutingKey;
    }


    @Transactional
    public long createTodo(Todo todo) throws JsonProcessingException {
        todo.setId(System.currentTimeMillis());
        long id = todoRepository.save(todo).getId();

        String todoAsJson = objectMapper.writeValueAsString(todo);
        rabbitTemplate.convertAndSend(rabbitExchange, rabbitRoutingKey, todoAsJson);
        return id;
    }
}
