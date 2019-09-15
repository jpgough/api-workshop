#!/bin/bash

set -o errexit


service_name="broker_app"
if [ -z `docker-compose ps -q $service_name` ] || [ -z `docker ps -q --no-trunc | grep $(docker-compose ps -q $service_name)` ]; then
  echo "No Pact broker is not running."
else
  echo "Yes, the Pact broker is running."
  docker stop $(docker ps | grep pact-broker | awk -F' ' '{print $1}') || echo "Failed to stop pact-broker"
  docker stop $(docker ps | grep postgres | awk -F' ' '{print $1}') || echo "Failed to stop postgres"
  docker-compose kill || echo "Failed to kill apps"
  yes | docker-compose rm -v || echo "Failed to kill apps"
fi


