package com.example.message.service;

import com.example.message.connector.ClockConnector;
import com.example.message.model.CurrentDateTime;
import com.example.message.model.Todo;
import com.example.message.repository.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class Orchestrator {

    private final ClockConnector clockConnector;
    private final TodoRepository todoRepository;

    public Orchestrator(ClockConnector clockConnector, TodoRepository todoRepository) {
        this.clockConnector = clockConnector;
        this.todoRepository = todoRepository;
    }

    /**
     * Orchestrate by getting the todo and then calling a rest endpoint.
     * @param todo
     */
    @Transactional
    public void processTodo(Todo todo) {
        CurrentDateTime currentDateTime = clockConnector.getCurrent();
        System.out.println("Current Date Time " + currentDateTime);
        todo.setId(System.currentTimeMillis());
        todoRepository.save(todo);
    }
}
