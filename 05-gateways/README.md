# Lab 5 Gateways

Lab 5 is a brief demo of using `docker-compose` to put a gateway in front of the Todo application.
Docker compose is a really useful tool for running multiple containers together.

### Step 1 - Adding a docker-compose.yaml

Create the following `docker-compose.yaml` in the root of the todo application

```yaml
version: '3'
services:
  todo-service:
    build:
      context: ./
      dockerfile: Dockerfile
    image: "todo-service:latest"
    ports:
      - "8080:8080"
  api-gateway:
    image: jpgough/api-workshop-gateway
    ports:
      - "8081:8081"
```

The services define the containers that should be launched and gives them a well known name. 
For example `task-service` will be used to send a request to the task service from the API Gateway.
You will notice that the task-service has a build, meaning that it will build your application based on your Dockerfile from lab 1.

We haven't built an `api-gateway` but one has been configured for this lab and deployed to [Dockerhub](https://hub.docker.com/r/jpgough/api-workshop-gateway).
The source code from the gateway can be found on [GitHub](https://www.github.com/jpgough/api-workshop-gateway).
The image has been setup to automatically build on each push to GitHub.

### Step 2 - Starting the Application

Now we have configured our `docker-compose.yaml` we start up the services by running `docker-compose up`.
Both containers will launch and a gateway has been deployed to front our todo API.
You can find out more about [Docker Compose here](https://docs.docker.com/compose/).

We should see that both `http://localhost:8080/hello` and `http://localhost:8081/tasks/hello` return the same result.

Fronting your application with a gateway is really simple and provides an opportunity to host multiple APIs.
The client only needs to understand the single endpoint. 
