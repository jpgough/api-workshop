# Lab 2 - Contracts

In lab 1 we created an API for tracking a todo list.
One thing you may have felt whilst writing the code was the requirement to do manual testing.
In this lab we will explore writing Contract Driven Tests for our todo list application.

## Lab 1 Recap

Before we start it's important to scope what should have been done in lab 1 (in an ideal solution/world).

- A `Todo` Controller should have been created and unit tested for accuracy
- The `Todo` Controller should have implemented the GET, POST and DELETE behaviour described in [Lab 1](../01-spring-boot/README.md).

## Step 1 - Configuring Spring Cloud Contracts

As always it's probably a good idea to commit the work from the previous lab if you have not already.

In our `pom.xml` you will need to check that you have the `spring-cloud-contract-maven-plugin` Maven plugin.

The `spring-cloud-contract-maven-plugin` is a Maven plugin to allow us to work with Spring Cloud Contracts.

This should appear in the build plugins section `pom.xml` as follows, if it does not then copy the configuration in:

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-contract-maven-plugin</artifactId>
            <version>3.1.4</version>
            <extensions>true</extensions>
            <configuration>
                <testFramework>JUNIT5</testFramework>
            </configuration>
        </plugin>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```

The `spring-cloud-starter-contract-verifier` test dependency is the Spring Cloud Contract verifier, it generates tests to confirm your application conforms to the contracts.

This should appear in your dependencies as follows:

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-contract-verifier</artifactId>
    <scope>test</scope>
</dependency>
```

With this configuration in place we are all setup to write our first contract.

## Step 2 - Writing your first contract

- Create a folder called `resources` under the test directory
- Inside the `resources` directory create a directory called `contracts`, this is where our contract tests will live.
- Create our first contract inside the `contracts` folder

`todo_list_is_empty.groovy`

```groovy
package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'GET'
        url '/todos'
    }
    response {
        status 200
        body('[]')
    }
}
```

The file represents a DSL specified in groovy for defining a contract.
One of the first manual tests we performed in our previous lab was to check the todos are initially empty.

Now we have our contract we need to provide some setup to run this.

## Step 3 - Creating the Basis for the Test

We need to setup a class that knows our `TodoController` is the subject under test.
Let this sink in and how it makes sense. We write a contract to define what interactions should happen. However, we need to configure the parts of the application that are going to verify the contracts.

Lets do this by creating a class called `ContractsBase.java`, which we prepare to be the basis for running our contracts.

```java
package com.jpgough.apiworkshop;

import com.jpgough.apiworkshop.controller.TodosController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;

public abstract class ContractsBase {

    TodosController todoController = new TodosController();

    @BeforeEach
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(this.todoController);
    }
}
```

> **Note:** the `ContractsBase` file should be put in the `test` folder

You may have noticed a couple of things, this is an `abstract class` and only contains a `@BeforeEach` annotation and no actual tests.
The tests are going to be generated from the contracts we have specified and the generated test will extend this base class.

As an alternative you could `@SpringBootTest` rather than `RestAssured` to configure and launch your controller under test.

The final step is to configure the `spring-cloud-contract-maven-plugin` plugin in the `pom.xml` to reference the new base class for testing.

```xml
<plugin>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-contract-maven-plugin</artifactId>
    <version>3.1.4</version>
    <extensions>true</extensions>
    <configuration>
        <testFramework>JUNIT5</testFramework>
        <baseClassForTests>com.jpgough.apiworkshop.ContractsBase</baseClassForTests>
    </configuration>
</plugin>
```

## Step 4 - Running the Tests and Checking the Output

The final step is to run the tests and view the results.

We can do this by running `mvnw test` and if everything works we should see a successful build and the test results.

We can then review the `target/surefire-reports/com.jpgough.apiworkshop.ContractVerifierTest.txt` report for a summary of the test results.

In the directory `target/generated-test-sources` we can find the test that was generated and executed

```java
public class ContractVerifierTest extends ContractsBase {

    @Test
    public void validate_todo_list_is_empty() throws Exception {
        // given:
            MockMvcRequestSpecification request = given();


        // when:
            ResponseOptions response = given().spec(request)
                    .get("/todos");

        // then:
            assertThat(response.statusCode()).isEqualTo(200);

        // and:
            DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
            assertThatJson(parsedJson).isEmpty();
    }

}
```

It is worth keeping an eye on this folder as we go through the next part of the workshop.

If a test doesn't work as expected you may have to take a look in this file to check the test is as expected and it is sometimes worth running `mvnw clean` to clear out the folder to force regenerating the test.
You can also execute the tests from this directory in your IDE.

It is also worth noting that the tests use [REST-assured](http://rest-assured.io) behind the scenes which is good library for testing APIs.

## Step 5 - Adding More Tests

Our `ContractsBase.java` allows us to cover a lot of negative test cases.
When we use all the other requests we should see some failures.

Try and add tests for the following API edge cases:

- `GET /todos/1` - Should return a 404, as an item does not exist
- `DELETE /todos/1` - Should return a 404, as an item does not exist
- `POST /todos` - Should return a 201 as the item does not exist

## Step 6 - Extensions

This extension is a little tricky as it now requires us to have a different starting state.

At this stage `ContractsBase.java` should be renamed to `ContractsEmptyBase.java` and we then need to create an additional new base class `ContractsExistingBase.java`

```java
public abstract class ContractsExistingBase {

    private TodosController todoController = new TodosController();

    @BeforeEach
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(this.todoController);
        todoController.createNewTodo(new Todo("Make the bed"));
    }

}
```

We will need to create two packages in our contracts to hold contracts for tests that have no pre-created todos and tests that do have pre-created todos

![Contract empty and entry](images/empty_entry.png)

As we now have more than one base class for our contract tests we'll need to configure the `spring-cloud-contract-maven-plugin` in the Maven `pom.xml` to specify the mappings between contracts and base class:

```xml
<plugin>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-contract-maven-plugin</artifactId>
    <version>3.1.4</version>
    <extensions>true</extensions>
    <configuration>
        <testFramework>JUNIT5</testFramework>
        <baseClassMappings>
                <baseClassMapping>
                    <contractPackageRegex>.*empty.*</contractPackageRegex>
                    <baseClassFQN>com.jpgough.apiworkshop.contracts.ContractsEmptyBase</baseClassFQN>
                </baseClassMapping>
                <baseClassMapping>
                    <contractPackageRegex>.*exists.*</contractPackageRegex>
                    <baseClassFQN>com.jpgough.apiworkshop.contracts.ContractsExistingBase</baseClassFQN>
                </baseClassMapping>
            </baseClassMappings>
    </configuration>
</plugin>
```

The documentation for this can be found [here](https://cloud.spring.io/spring-cloud-contract/reference/htmlsingle/#how-to-protocol-convention-producer-with-contracts-stored-locally).

The scenarios we can now test include:

- GET all todos and verify
- GET a specific ToDo and verify the description and a status code
- DELETE a specific ToDo

It is also possible to have multiple tests executed together in scenarios using Spring Cloud Contract.

## Further reading

You can check out lots of [spring cloud contract](https://spring.io/projects/spring-cloud-contract#overview) examples at the github page: <https://github.com/spring-cloud-samples/spring-cloud-contract-samples>

An option to explore is the use of the [Pact Broker](https://cloud.spring.io/spring-cloud-contract/reference/htmlsingle/#how-to-use-pact-broker) to store and share Pact (i.e. contract) definitions.
