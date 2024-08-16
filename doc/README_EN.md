# Product-ms

Read the Portuguese version [here](README_PT).

🎯 **Description**:  
**Product-ms** is a microservice for managing products. It allows you to create, search, deactivate, and update products. The main focus of this project is learning unit testing techniques with JUnit5, validating fields in DTOs, and handling validation errors.

🔧 **Features**:  
- Full CRUD for products.
- Products are logically deleted: a field `available` is used to set whether a product is active (`true`) or inactive (`false`).

## Technologies Used

- **Java** 21
- **Spring Boot** 3.3.2
- **Maven** 3.9.7

## Dependencies

- **SpringDoc Swagger OpenAPI** 2.6.0: For API documentation
- **H2Database**: For testing environment
- **PostgreSQL**: For cloud environment on Railway
- **Lombok**: To eliminate boilerplate code
- **ModelMapper** 3.2.1: To facilitate data transfer between DTO and Entity
- **Fixture-factory** 3.1.0: For creating fixtures or templates for dummy data and facilitating test creation
- **Spring Validation**: For validating DTO fields
- **Spring Test**: For unit and integration testing

## Prerequisites

To run **Product-ms**, you need:
- Java 17+
- Spring Boot 3.x.x
- IDE (such as IntelliJ, Eclipse, etc.)
- Maven dependency manager
- HTTP client (such as HTTPie, Insomnia, etc.) or browser to test the endpoints (the API is documented with Swagger)

## Installation and Configuration

1. Clone the repository:
    ```bash
    git clone https://github.com/Alexandre-Gaspar/product-ms.git
    ```

2. Open the project in an IDE (such as IntelliJ or Eclipse) and reload Maven to download the dependencies (usually available through a "Play" button in the IDE interface).

3. Alternatively, open the terminal, navigate to the cloned project folder, and run:
    ```bash
    mvn spring-boot:run
    ```

   This will download the dependencies and start the Tomcat server.

## Usage

With the server running, open your browser and go to:
[http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)

In the Swagger interface, you will find all the endpoints listed with their request and response details.

## Endpoints

- **POST** `/products`  
  Creates a new product.  
  **Response**: 201 Created

- **GET** `/products`  
  Retrieves all products.  
  **Response**: 200 OK

- **GET** `/products/{id}`  
  Retrieves a product by its ID.  
  **Response**: 200 OK

- **POST** `/products/{id}`  
  Updates a product by specifying the ID.  
  **Response**: 200 OK

- **DELETE** `/products/{id}`  
  Deactivates a product (instead of deleting it physically).  
  **Response**: 200 OK

## Tests

To run tests, ensure the environment is set up and configured with the project. Navigate to `src/test/java/com/github/alex3g/product_ms` and run the `ProductMsApplicationTests` class.

## Contribution

This project is for learning purposes only. However, if you want to contribute or make improvements, feel free to reach out.

## Contact

🔗 [LinkedIn](https://www.linkedin.com/in/alex--gaspar/)
