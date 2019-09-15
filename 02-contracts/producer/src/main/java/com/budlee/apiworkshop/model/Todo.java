package com.budlee.apiworkshop.model;

import java.util.Objects;

public class Todo {
    private String message;

    public Todo() {
        //Used for serialization
    }

    public Todo(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return Objects.equals(message, todo.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }
}
