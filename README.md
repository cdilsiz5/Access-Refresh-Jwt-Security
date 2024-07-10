# Access&Refresh Jwt Token Security

## Project Overview
This project is a Spring Boot application designed for handling user authentication and management. It provides a robust security configuration using JWT for secure API access.

## Technical Features

- **Spring Boot**: Utilizes Spring Boot for rapid application development and effortless dependency management.
- **Security**: Integrates Spring Security for authentication and authorization, employing JSON Web Tokens (JWT) for stateless authentication.
- **Database**: Configured with H2 in-memory database for quick setup and testing.
- **Data Access**: Uses Spring Data JPA for database interactions and Hibernate for ORM.
- **Documentation**: API documentation is automatically generated using SpringDoc OpenAPI.

## Security Configuration

- **JWT Integration**: Leverages JSON Web Tokens to secure REST APIs, ensuring that all sensitive operations require token authentication.
- **Stateless Session Management**: Configured to use stateless session management to enhance security and scalability.
- **CSRF Protection**: CSRF protection is disabled as the application uses JWT, which is inherently protected against CSRF attacks.

## Dependencies and Versions

- **Spring Boot**: 3.2.5
- **Java Version**: 19
- **MapStruct**: 1.5.3.Final
- **H2 Database**: 2.1.214
- **Lombok**: 1.18.28
- **SpringDoc OpenAPI**: 2.2.0
- **JSON Web Token Support**: jjwt-api 0.11.5, jjwt-impl 0.11.5 (runtime), jjwt-jackson 0.11.5 (runtime)

## Getting Started

To get a local copy up and running follow these simple steps.

## Installation

1. Clone the repository:
```
git clone https://github.com/cdilsiz5/ID3.git
cd ID3
```
2. Build the project using Maven:
 ```
mvn clean install
```
3. Run the application:
```
mvn spring-boot:run
```

### Prerequisites

- JDK 19: Ensure Java Development Kit (JDK) is installed on your machine.
- Maven: Ensure Maven is installed for managing project dependencies.

## API Endpoints Overview

This documentation outlines the available REST API endpoints for account management and authentication services. Each endpoint is designed to perform specific operations related to user accounts and authentication processes.

### Account Resource
- **POST `/api/v1/id3/accounts`**: Create a new account.
- **POST `/api/v1/id3/accounts/transfer`**: Transfer balance between accounts.
- **GET `/api/v1/id3/accounts/user/{userId}`**: Fetch accounts by user ID.
- **DELETE `/api/v1/id3/accounts/{id}`**: Delete an account by ID.

### CRUD REST APIs for Auth
- **POST `/api/v1/id3/authentication/refreshToken`**: Request a new refresh token.
- **POST `/api/v1/id3/authentication/logout/{userId}`**: Logout a user.
- **POST `/api/v1/id3/authentication/login`**: Login a user.
- **GET `/api/v1/id3/authentication/validate`**: Validate a user token.

### User Resource
- **PUT `/api/v1/id3/users/{id}`**: Update user details.
- **POST `/api/v1/id3/users/register`**: Register a new user.
- **GET `/api/v1/id3/users`**: Fetch user details.

  
