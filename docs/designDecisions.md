# Project-One
## Design decisions

### APIs
To implement our APIs application, we first created the specifications of the routes. So like the readme mentionned it we did 2 APIs endpoints, one for auth and other one of our business logic.

#### Routes

Then we need to define our entities to be able to speak with the database and define the relations between our RTOs (from the specification).
#### Entites

They are located in the `entities` diretory, and are mostly boilerplate code.

For that reason, we made ample use of Lombok, an annotation pre-processor to automatically generate our boilerplate code for us.

#### Endpoints
They are located in the `api/endpoints` directory, They are just an override of the methods defined with the specification. With openapi-generator the code is generated from the specification. And the we rewrite the functions on `api/endpoints` to add the logic of the endpoint.

They link the data received from the requests (RTOs) and transform them into entities. That's done with some code that create an entity from the data given. This will help us to talk with the database.

#### Access control

We implemented basic levels of acess control in the AuthFilter, located in the `security/FilterConfig` file. This will check for the token to be valid and get the informations in the token so we put these in the HttpRequest object so we can get these anywhere in our application.

We implemented it in such a way that we capture all URLs with the filter, and sequentially check if the user can access them, and if not, reply with an 401 Unauthorized error.

#### Integration Layer

Our integration layer is the repositories implementing CrudRepository to be able to do some CRUD operations relatively simple.

#### Buisness Layer

Our business layer is in our endpoints, because we make the business logic `api/endpoints` and retrieve the responses accordingly. 

---
[Return to the main readme](https://github.com/capito27/Teaching-HEIGVD-AMT-2019-Project-One/blob/master/README.md)
