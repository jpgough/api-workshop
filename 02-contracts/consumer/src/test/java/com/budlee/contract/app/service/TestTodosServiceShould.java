package com.budlee.contract.app.service;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.budlee.contract.app.model.Todo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "todo-api-producer-pact", port = "8083")
public class TestTodosServiceShould {

    @Pact(consumer = "todo-api-consumer-pact")
    public RequestResponsePact updateAToDoRequest(PactDslWithProvider builder) {
        return builder
                .given("an todo of id 0 has already been created")
                .uponReceiving("a request to update the todo")
                .matchPath("/todos/0")
                .method("PUT")
                .headers("Content-Type", "application/json")
                .body("{\"message\":\"Make all the beds\"}")
                .willRespondWith()
                .status(204)
                .toPact()
                ;
    }

    TodosService todosService = new TodosService();

    @BeforeEach
    public void setUp() {
    }


    @Test
    @PactTestFor
    public void updateTodo() {
        //NO CONTENT
        assertEquals(this.todosService.updateTodo(0, new Todo("Make all the beds")).getStatusCodeValue(), 204);
    }
}