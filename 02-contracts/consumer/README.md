# A Client that produces contracts

This is a client application that is producing contracts.
Here the consumer is creating a Pact that it is publishing to the Pact Broker which the Producer needs to fulfil

Run the following command:
```shell script
./gradlew test
```

This will start a Pact broker and publish the Consumers contracts to the application

when the lab has finished then stop the dockers services by running

```shell script
./gradlew stopPactBroker
```