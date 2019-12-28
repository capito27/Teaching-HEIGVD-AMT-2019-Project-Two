#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

TIME_TO_WAIT=60
WAIT_INCREMENTS=5
HEALTHY_CHECK_VALID_COUNT=1

cd $DIR


printf "Cleaning repo..."
# First, we clean the repo
mvn -f app/pom.xml clean --quiet > /dev/null 2>&1
mvn -f auth/pom.xml clean --quiet > /dev/null 2>&1

printf "done !\nRebuilding DB and traefik..."

# Secondly, we stop and rebuild the dev docker environment (as well as stop the prod environment, for port collision safety)
# (also, since we're deploying, we copy the 1Mil entries, so that we have enough datasets for loadtesting)
docker-compose -f docker/topologies/amt-projectTwo/docker-compose.yml --log-level ERROR down > /dev/null 2>&1
docker-compose -f docker/topologies/amt-projectTwo/docker-compose.yml --log-level ERROR up --build --force-recreate -d db traefik > /dev/null 2>&1

printf "done !\nWaiting for DB to be up (should take around a minute)"

WAITED_TIME=0
until [ $WAITED_TIME -ge $TIME_TO_WAIT ] ; do    
    sleep $WAIT_INCREMENTS
    printf "."
done

printf "done !\nBuilding Auth API...\n"
mvn -f auth/pom.xml package
cp auth/target/auth-0.0.1-SNAPSHOT.jar docker/images/auth/

printf "done !\nStarting Auth API...\n"
docker-compose -f docker/topologies/amt-projectTwo/docker-compose.yml --log-level ERROR up --build --force-recreate -d backend-auth > /dev/null 2>&1

# Sleep 10 seconds to ensure the auth service is up and running
sleep 10

printf "done !\nBuilding App API...\n"
mvn -f app/pom.xml package
cp app/target/app-0.0.1-SNAPSHOT.jar docker/images/app/

printf "done !\nStarting Auth API...\n"
docker-compose -f docker/topologies/amt-projectTwo/docker-compose.yml --log-level ERROR up --build --force-recreate -d backend-app > /dev/null 2>&1

printf "API deployed ! Should be available within 10 seconds"


