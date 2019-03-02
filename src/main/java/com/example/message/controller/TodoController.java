package com.example.message.controller;

import com.example.message.model.Todo;
import com.example.message.service.TodoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping("/api/todos")
    public long createTodo(@RequestBody Todo todo) throws JsonProcessingException {
        return todoService.createTodo(todo);
    }
}
