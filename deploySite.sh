#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

TIME_TO_WAIT=60
WAIT_INCREMENTS=5
HEALTHY_CHECK_VALID_COUNT=4

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
HEALTHY_COUNT=0
until [ $WAITED_TIME -ge $TIME_TO_WAIT ] ; do    
    sleep $WAIT_INCREMENTS
    printf "."
    # Check if service is healthy, thus, the DB is up, if not, reset the healthy counter
    if [ $(docker inspect --format='{{.State.Health.Status}}' amt-projecttwo_db_1) == "healthy" ] ; then
        HEALTHY_COUNT=$((HEALTHY_COUNT+1))
    else
        HEALTHY_COUNT="0"
    fi

    # Check if the service has been found healthy enough times in a row, we're done
    if [ $HEALTHY_COUNT -ge $HEALTHY_CHECK_VALID_COUNT ] ; then
        break;
    fi
    WAITED_TIME=$((WAITED_TIME + WAIT_INCREMENTS))
done

# If the counter is still a 0 after the loop, something ain't right, and we bail
if [ $HEALTHY_COUNT -eq "0" ] ; then
    printf "Failed!\nAborting.\n"
fi


printf "done !\nBuilding Auth API...\n"
mvn -f auth/pom.xml package
cp auth/target/auth-0.0.1-SNAPSHOT.jar docker/images/auth/

printf "done !\nBuilding App API...\n"
mvn -f app/pom.xml package
cp app/target/app-0.0.1-SNAPSHOT.jar docker/images/app/

printf "done !\nStarting both API...\n"
docker-compose -f docker/topologies/amt-projectTwo/docker-compose.yml --log-level ERROR up --build --force-recreate -d backend-auth backend-app > /dev/null 2>&1

printf "API deployed ! Should be available within 10 seconds"


