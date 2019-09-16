package com.budlee.contract.app.service;

import com.budlee.contract.app.model.Todo;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

public class TodosService {

    public Map<Integer, Todo> getAllTodos() {
        return new RestTemplate()
                .getForObject("http://localhost:8083/todos", Map.class);
    }

    public ResponseEntity<Void> updateTodo(Integer id, @RequestBody Todo todo) {
        return new RestTemplate()
                .exchange(
                        RequestEntity.put(URI.create("http://localhost:8083/todos/" + id))
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(todo), Void.class
                );
    }


}