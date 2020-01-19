# Teaching-HEIGVD-AMT-2019-Project-Two
## Project description
This project is a simple REST API, implemented purely using the Spring Framwork, in the context of the AMT Course, followed at HEIG-VD. 

The purpose of this project was to introduce students to the Spring framework, through SpringBoot, as well as REST API developpement through the openAPI / swagger system.

## Project dependencies
To run this project, you MUST have the following installed : 
- maven
- docker-compose


This project depends on the following ports being available within the base system :
- 80 for the API endpoints
- 3306 for the SQL DB passthrough 

## How to manually build this project
This project is distributed as a jar
To build the project, users MUST have Maven installed.

To build this project, users MUST run the following command (in the project root directory) :
```
mvn -f app/pom.xml clean package -DskipTests 
mvn -f auth/pom.xml clean package -DskipTests 
```

## How to test  this project
Due to the cucumber JVM testing depending on  the dockerised envirement to be available, the project may be tested using standard deploy script (`deploySite.sh`), found at the root of the project.

Along with cucumber JVM testing, this script also generates a cucumber coverage report.
Said report is integrated into the final jar file, and may be accessed, when running the APIs,  at the following path from any web browser :
 
- The Auth API report : http://localhost/api/auth/cucumber-html-reports/overview-features.html
- The Application API report : http://localhost/api/app/cucumber-html-reports/overview-features.html

## How to run this project
To run this program, due to depending on a specific dockerised environment, you MUST run it from the `deploySite.sh` script, found at the root of the project.

The script will build the project, deploy the dockerized infrastructure and the website, along with a default dataset of over 1 million entries.

You can log into  the AUTH API with the following default users (`<username>:<password>`): 

-  admin:password for a admin user, containing 1 million match entries
-  user:password for an standard user

The Auth API definition may be accessed here : http://localhost/api/auth
The Application API definition may be accessed here : http://localhost/api/app

## Report

### Design Decisions

A list of our design decisions may be found at [the following link](https://github.com/capito27/Teaching-HEIGVD-AMT-2019-Project-One/blob/master/docs/designDecisions.md).

### Implementation details

The details of our implementation may be found at [the following link](https://github.com/capito27/Teaching-HEIGVD-AMT-2019-Project-One/blob/master/docs/Implementation.md).

### Testing strategy

Our testing strategy is explicited at [the following link](https://github.com/capito27/Teaching-HEIGVD-AMT-2019-Project-One/blob/master/docs/TestingStrategy.md).

### Performance experiments

The results of our experiments about data paging may be found at [the following link](https://github.com/capito27/Teaching-HEIGVD-AMT-2019-Project-One/blob/master/docs/PerformanceTest.md).

