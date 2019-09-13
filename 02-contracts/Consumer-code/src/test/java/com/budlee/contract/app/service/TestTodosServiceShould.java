package com.budlee.contract.app.service;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import com.budlee.contract.app.model.Todo;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestTodosServiceShould {

    @Rule
    public PactProviderRuleMk2 mockProvider =
            new PactProviderRuleMk2("todo-api-producer-pact", "localhost", 8083, this);

    @Pact(consumer = "todo-api-consumer-pact")
    public RequestResponsePact updateAToDoRequest(PactDslWithProvider builder) {
        return builder
                .given("an todo of id 1 has already been created")
                .uponReceiving("a request to update the todo")
                .matchPath("/todos/1")
                .method("PUT")
                .headers("Content-Type", "application/json")
                .body("{\"message\":\"Make all the beds\"}")
                .willRespondWith()
                .status(204)
                .toPact()
                ;
    }

//    @Pact(consumer = "todo-api-consumer-pact-update-no")
//    public RequestResponsePact updateAToDoThatDoesNotExist(PactDslWithProvider builder) {
//        return builder
//                .given("an todo of id 1 has already been created")
//                .uponReceiving("a request to update a todo that does not exist")
//                .matchPath("/todos/1000")
//                .method("PUT")
//                .headers("Content-Type", "application/json")
//                .body("{\"message\":\"Make all the beds\"}")
//                .willRespondWith()
//                .status(404)
//                .toPact();
//    }

    TodosService todosService = new TodosService();

    @Before
    public void setUp() {
    }


    @Test
    @PactVerification
    public void updateTodo() {
        //NO CONTENT
        assertEquals(this.todosService.updateTodo(1, new Todo("Make all the beds")).getStatusCodeValue(), 204);
    }

//    @Test
//    @PactVerification
//    public void updateTodoThatDoesNotExist() {
//        //NO CONTENT
//        assertEquals(this.todosService.updateTodo(1000, new Todo("Make all the beds")).getStatusCodeValue(), 404);
//    }
}