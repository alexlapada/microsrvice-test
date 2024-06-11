# Test Task

**Task 1: Blog application**

- Design a data model for a blog application and implement the corresponding JPA entities.
- Write a Spring Data JPA repository query to find all articles published before a certain date.
- Create a REST API that enables CRUD operations for articles.
- Implement exception handling in Spring Boot to return custom error messages for a REST API.

## Authentication
There are two available users:
Admin (username = admin, password = admin)
User (username = user, password = user)

to get JWT token you should call login endpoint
```shell
curl --location 'http://localhost:2929/authentication/login' \
--header 'Content-Type: application/json' \
--data '{
"password": "admin",
"username": "admin"
}'
```

then you can use JWT token to call other api endpoint

here is only one endpoint restricted by ROLE is
http://localhost:2929/articles/before-date?date=2024-06-11
example to call 
```shell
curl --location 'http://localhost:2929/articles/before-date?date=2024-06-11' \
--header 'Authorization: Bearer <JWT>'
```

**Task 2: Microservice application**

- Develop a simple microservice with Spring Boot and register it with an Eureka server
- Integrate Spring Security into the application and configure authentication via a database.
- Create authorization logic to restrict access to specific API endpoints based on user roles.
- Describe how you would configure Spring Boot applications for deployment in a cloud environment such as AWS or Azure.

Here is Discovery Server live on port 3333
so before run blog api you should run discovery server to be able to register all applications

Eureka UI http://localhost:3333/
