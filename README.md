# Spring Boot Product Management Application

This is a Spring Boot application for managing products. It provides a RESTful API for performing CRUD operations on products.

## Prerequisites
- Java JDK 17 or higher
- Maven
- Docker (for running Postgresql)
- Postman for testing

## Getting Started

1. Clone the repository:

```https://github.com/nejcintihar/SpringBootCRUD.git```

2. Navigate to the project directory:

```cd SpringBootCRUD```

3. Build the application using Maven:

```./mvnw clean package```

4. Give executable permission to init-user.sh

```chmod +x docker-entrypoint-initdb.d/init-user.sh```

5. Enter the same user and password credentials in docker-compose and application.properties

dcoker-compose.yml:

> POSTGRES_USER: YOUR_USER
> 
> POSTGRES_PASSWORD: YOUR_PASSWORD

application.properties:

> spring.datasource.username=YOUR_USER
>
> spring.datasource.password=YOUR_PASSWORD

6. Run the docker-compose file for Postgresql

```docker compose up -d```

6. Run the ProductApplication

The application will start running on http://localhost:8080.

## API Endpoints
The following endpoints are available:

POST /products: Create a new product

GET /products: Retrieve all products

GET /products/{id}: Retrieve a product by ID

PATCH /products/{id}: Update a product by ID

DELETE /products/{id}: Delete a product by ID

## Unit and Integration tests

Unit and Integration tests are located in src/test/java/com/dev/product/
