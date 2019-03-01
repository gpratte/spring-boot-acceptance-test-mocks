package com.example.message.service;

import com.example.message.model.Todo;
import com.example.message.repository.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Transactional
    public long createTodo(Todo todo) {
        todo.setId(System.currentTimeMillis());
        return todoRepository.save(todo).getId();
    }
}
