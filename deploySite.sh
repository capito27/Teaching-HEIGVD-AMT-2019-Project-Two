#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

cd $DIR

# Website URL
WebsiteURL=http://localhost:8080/Project-One/index

printf "Cleaning repo..."
# First, we clean the repo
mvn -f app/pom.xml clean --quiet

printf "done !\nBuilding WAR file (Without Arquillian testing)..."
# Then, we run the package target without arquillian, so that we can get a war file
mvn -f app/pom.xml package -DskipTests --quiet


printf "done !\nRebuilding prod docker environment (takes up to a minute)...\n"
# Secondly, we stop and rebuild the dev docker environment (as well as stop the prod environment, for port collision safety)
# (also, since we're deploying, we copy the 1Mil entries, so that we have enough datasets for loadtesting)

cp docker/images/mysql/2_gen_data.sql docker/images/mysql/dump/2_gen_data.sql
docker-compose -f docker/topologies/amt-projectOne-dev/docker-compose.yml --log-level ERROR down >/dev/null
docker-compose -f docker/topologies/amt-projectOne-prod/docker-compose.yml --log-level ERROR down >/dev/null
docker-compose -f docker/topologies/amt-projectOne-prod/docker-compose.yml --log-level ERROR up --build -d

printf "Website deployment in progress (takes up to 2 minutes)..."

a=0
# This code waits for both the website and the database to be up and accessible
until $(curl --output /dev/null --silent --head --fail $WebsiteURL);
do
    sleep 1
    a=$((a+1))
    if [[ $a -ge 120 ]]
    then
        break;
    fi
done

if [[ $a -ge 120 ]]
then
    printf "Failed !\n"
else
    printf "Done !\n Website is available at : $WebsiteURL\n"
fi


