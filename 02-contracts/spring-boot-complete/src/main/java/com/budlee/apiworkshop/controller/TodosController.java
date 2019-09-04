package com.budlee.apiworkshop.controller;

import com.budlee.apiworkshop.model.Todo;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class TodosController {

    AtomicInteger incrementKey = new AtomicInteger(1);
    Map<Integer, Todo> todosMap = new HashMap<>(20);

    @GetMapping("/todos")
    public Map<Integer, Todo> getAllTodos() {
        return todosMap;
    }

    @PostMapping(value = "/todos")
    public ResponseEntity<Void> createNewTodo(@RequestBody Todo todo) {
        final var key = incrementKey.getAndIncrement();
        todosMap.put(key, todo);
        final var uri = URI.create("/todos/" + key);
        return ResponseEntity
                .created(uri)
                .build();
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<Todo> getTodo(@PathVariable(value = "id") Integer id) {
        return todosMap.containsKey(id) ?
                ResponseEntity.ok(todosMap.get(id)) :
                ResponseEntity.notFound().build();
    }

    @PutMapping(value = "/todos/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateTodo(@PathVariable(value = "id") Integer id, @RequestBody Todo todo) {
        todosMap.replace(id, todo);
        return ResponseEntity.noContent().build();
    }


}