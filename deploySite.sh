#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

cd $DIR


printf "Cleaning repo..."
# First, we clean the repo
mvn -f app/pom.xml clean --quiet
mvn -f auth/pom.xml clean --quiet

printf "done !\nRebuilding DB and traefik (takes about a minute)...\n"

# Secondly, we stop and rebuild the dev docker environment (as well as stop the prod environment, for port collision safety)
# (also, since we're deploying, we copy the 1Mil entries, so that we have enough datasets for loadtesting)
docker-compose -f docker/topologies/amt-projectTwo/docker-compose.yml --log-level ERROR down >/dev/null
docker-compose -f docker/topologies/amt-projectTwo/docker-compose.yml --log-level ERROR up --build --force-recreate -d db traefik

sleep 60

printf "done !\nBuilding WAR files..."
mvn -f app/pom.xml package
cp app/target/app-0.0.1-SNAPSHOT.jar docker/images/app/
mvn -f auth/pom.xml package
cp auth/target/auth-0.0.1-SNAPSHOT.jar docker/images/auth/


printf "done !\n Building API containers...\n"

# Secondly, we stop and rebuild the dev docker environment (as well as stop the prod environment, for port collision safety)
# (also, since we're deploying, we copy the 1Mil entries, so that we have enough datasets for loadtesting)
docker-compose -f docker/topologies/amt-projectTwo/docker-compose.yml --log-level ERROR up --build --force-recreate -d backend-app backend-auth



printf "API deployment in progress (should take less than 10 seconds)..."


