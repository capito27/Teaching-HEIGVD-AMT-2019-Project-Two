#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

printf "Cleaning repo..."
# First, we clean the repo
mvn -f app/pom.xml clean --quiet

printf "done !\nRebuilding dev docker environment (takes up to a minute)..."
# Secondly, we stop and rebuild the dev docker environment (as well as stop the prod environment, for port collision safety)
# (also, when testing, and not in prod, we don't copy the 1Mil entries, so that the tests actually work)
rm -f $DIR/docker/images/mysql/dump/2_gen_data.sql
docker-compose -f docker/topologies/amt-projectOne-prod/docker-compose.yml --log-level ERROR down >/dev/null
docker-compose -f docker/topologies/amt-projectOne-dev/docker-compose.yml --log-level ERROR down >/dev/null
docker-compose -f docker/topologies/amt-projectOne-dev/docker-compose.yml --log-level ERROR up --build --force-recreate -d

printf "done !\nBuilding WAR file..."
# Then, we run the package target without tests, so that we can get a war file quickly
mvn -f app/pom.xml package -DskipTests --quiet

printf "done !\nLaunching the tests !\n"
# Finally, we run the mvn test target
mvn -f app/pom.xml test
