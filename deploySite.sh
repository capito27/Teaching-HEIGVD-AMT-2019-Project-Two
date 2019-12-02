#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

cd $DIR


printf "Cleaning repo..."
# First, we clean the repo
mvn -f app/pom.xml clean --quiet
mvn -f auth/pom.xml clean --quiet

printf "done !\nBuilding WAR files..."
# Then, we run the package target without arquillian, so that we can get a war file
mvn -f app/pom.xml package --quiet > /dev/null
cp app/target/app-0.0.1-SNAPSHOT.jar docker/images/app/
mvn -f auth/pom.xml package --quiet > /dev/null
cp auth/target/auth-0.0.1-SNAPSHOT.jar docker/images/auth/



printf "done !\nRebuilding prod docker environment (takes up to a minute)...\n"

# Secondly, we stop and rebuild the dev docker environment (as well as stop the prod environment, for port collision safety)
# (also, since we're deploying, we copy the 1Mil entries, so that we have enough datasets for loadtesting)
cp docker/images/mysql/2_gen_data.sql docker/images/mysql/dump/2_gen_data.sql
docker-compose -f docker/topologies/amt-projectTwo/docker-compose.yml --log-level ERROR down >/dev/null
docker-compose -f docker/topologies/amt-projectTwo/docker-compose.yml --log-level ERROR up --build -d

printf "API deployment in progress (should take less than 10 seconds)..."


