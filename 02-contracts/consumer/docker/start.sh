#!/bin/bash

set -o errexit

service_name="broker_app"
if [ -z `docker-compose ps -q $service_name` ] || [ -z `docker ps -q --no-trunc | grep $(docker-compose ps -q $service_name)` ]; then
  echo "No Pact broker is not running."
  docker-compose up -d
  echo "Waiting for 30 seconds for Broker to boot up"
  sleep 30
else
  echo "Yes, the Pact broker is running."
fi
