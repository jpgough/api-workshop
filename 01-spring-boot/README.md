# Lab 1 Spring Boot

We are going to create a project using Spring Boot. Take a moment to think about how you have created projects in the past....

* Created from a random template on the internet?
* Copy and pasted from StackOverflow until something worked?
* Found a project within an organisation and used the same thing (just renaming a few things)

Our workshop starts with a empty folder. We are going to use [SpringInitalizr](<https://start.spring.io>) to create our project and get started.

If you have not completed the [pre-requisites](../prerequisites/README.md) please complete these steps first.

## Step 1 - Creating our First Controller

In the project we will create a small REST controller.
Spring boot works by scanning classes and looking for annotations it recognises.
Based on these annotations it will be opinionated and choose what it thinks the right set of configuration should look like.

Usually controllers would live in their own package, you should create the `WorkshopController.java` in your project.

```java
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WorkshopController {

    @RequestMapping("/hello")
    @ResponseBody
    public String helloWorld() {
        return "Hello World";
    }

}
```

We can now start our project by running `ApiworkshopApplication.java` (or the class which contains the main method).
In a couple of seconds you should be able to visit [http://localhost:8080/hello](http://localhost:8080/hello)

![Hello World](01C-hello-world.png)

## Step 2 - Building a (slightly) more advanced API

We will create a very simple todo list application.
The application doesn't need to persist any data, though if you wanted to continue this exercise afterwards that is one extension point.

Here are the operations that we will look to implement:

* `GET /todos` Returns a list of todo items, initially the list will be empty
* `POST /todos` Creates a todo item
* `GET /todos/1` Returns the todo item with the ID 1
* `GET /todos?done=false` Returns a list of todo items, excluding those that are already done
* `DELETE /todos/1` Remove the todo item 1

### Design considerations

* You will need a POJO to represent the task (which contains an id and a description)
* You will need to explore the `@PathVariable` annotation
* You will probably want to factor out the Tasks features from the actual controller.
This will allow you to test independently.

You can test your API from your IDE or install a free tool like [Restlet](<https://chrome.google.com/webstore/detail/restlet-client-rest-api-t/aejoelaoggembcahagimdiliamlcdmfm?hl=en>)
into your browser.

### Step 3 (Optional) - Deploying with Docker

In order to build a docker container we first need to test running the application as a jar locally.
There are two options for building locally:

1. run `gradle build` from the command line (requires gradle to be installed)
1. run `gradle build` from the IDE

The following jar file should now be created: `build/libs/apiworkshop-0.0.1-SNAPSHOT.jar`
You can run this jar using `java -jar build/libs/apiworkshop-0.0.1-SNAPSHOT.jar` the Spring Boot gradle plugin will have packaged everything for you to run your REST API.
The application will now be running and testable on <http://localhost:8080/hello>.

The following `Dockerfile` can now be created in the root of the project.

```docker
FROM adoptopenjdk:11-jre-hotspot
RUN mkdir /opt/app
ADD build/libs/apiworkshop-0.0.1-SNAPSHOT.jar /opt/app/app.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "/opt/app/app.jar" ]
```

The docker image can now be built with the following command:

`docker build -t apiworkshop:v1 .`

Once the build is created our image will be visible by running `docker images`.

```shell
$ docker images
REPOSITORY                                                TAG                 IMAGE ID            CREATED             SIZE
apiworkshop                                               v1                  38a02d5fb614        5 minutes ago       622MB
```

We can now run our image using the following docker command

`docker run -p 8080:8080 -t apiworkshop:v1`

Note the -p command sets up the port mapping. <http://localhost:8080/hello> - will now be the docker hosted application.
We can view our running docker containers using the command `docker ps`

```shell
$ docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED              STATUS              PORTS                    NAMES
46c5781e269f        apiworkshop:v1      "sh -c 'java $JAVA_O…"   About a minute ago   Up About a minute   0.0.0.0:8081->8080/tcp   peaceful_stonebraker
```

If you need to restart/stop the container run `docker kill <container ID>`.

### Step 4 (Optional) - Building with Docker

One limitation with our above solution is that we have to remember to build both with gradle and then with docker.
This is both easy to forget and makes creating a build pipeline that bit more complex.

There is a solution, and that is that we modify the `Dockerfile` to both build the java code and create the runnable image.

```docker
FROM gradle:jdk11 as builder

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build
```

If we add the above to the beginning of our existing Dockerfile it will have the effect of creating a _Multistage build_ that is we will use the result of one stage in the stage that packages up the jar for running.
There is a nice blog walking through these steps [here](http://paulbakker.io/java/docker-gradle-multistage/) along with an article that provides a decent overview of [best practices](https://blog.docker.com/2019/07/intro-guide-to-dockerfile-best-practices/).
The above will now pull in gradle and build our project into the `/home/gradle/src`.

We now have to include the output from the gradle build into our packaging stage of the java image.
We can replace the `ADD` command with a `COPY` to move the jar from the gradle build area into our image.
The complete Dockerfile would look like this.

```docker
FROM gradle:jdk11 as builder

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build

FROM adoptopenjdk:11-jre-hotspot
RUN mkdir /opt/app
COPY --from=builder /home/gradle/src/build/libs/apiworkshop-0.0.1-SNAPSHOT.jar /opt/app/app.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "/opt/app/app.jar" ]
```

We can now run the image as we did in step 5, only this time when we run `docker build` it will both build the jar and create the runnable docker image.
There are also no magic environment variables or steps requiring a specific setup on a machine.
Containerizing builds provides a reproducible way of creating a docker image.
