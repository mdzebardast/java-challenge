### Employee App REST API

This is a Spring Boot application that provides a REST API for managing employee records.
The API allows users to create, retrieve, update, and delete employee records in an in-memory H2 database.

### Requirements
 
- Java 8

### Installation

- Clone this repository: git clone https://github.com/mdzebardast/java-challenge.git
- Navigate to the project directory: cd java-challenge
- Build the application: mvn clean install
- Run the application: mvn spring-boot:run

The application will start up and the REST API will be available at http://localhost:8080/api/v1/employees.
- Swagger UI : http://localhost:8080/swagger-ui.html
- H2 UI : http://localhost:8080/h2-console

### Libraries used

- Spring Boot 2.1.4.RELEASE
- Spring Boot Test
- Swagger API
- Spring security OAuth2 libraries

### Features
These services can perform:

- Employee CRUD operations.

### Security

This application uses OAuth2 to protect Rest API.
