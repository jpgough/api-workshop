package com.budlee.apiworkshop.contract.pact;

import com.budlee.apiworkshop.controller.TodosController;
import com.budlee.apiworkshop.model.Todo;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;


public abstract class ContractBase {

    TodosController todoController = new TodosController();

    @Before
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(this.todoController);
        todoController.createNewTodo(new Todo("Mkae the bed"));
    }
}
