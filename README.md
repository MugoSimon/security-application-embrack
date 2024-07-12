# Security Application Embrack

Welcome to the Security Application Embrack project! This project provides a secure RESTful API with JWT-based authentication and role-based access control using Spring Boot and Spring Security.

## Table of Contents

- [Overview](#overview)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
    - [Running the Application](#running-the-application)
- [Configuration](#configuration)
- [Endpoints](#endpoints)
- [Testing](#testing)
- [License](#license)
- [Dad Joke](#dad-joke)

## Overview

The Security Application Embrack project implements a REST API for user authentication and authorization using JSON Web Tokens (JWT). It includes endpoints for user login and role-based access to different parts of the application.

## Technologies Used

- Java 17
- Spring Boot 3.3.1
- Spring Security
- Spring Data JPA
- H2 Database (for development)
- MySQL Connector (for production)
- JSON Web Token (JWT) for authentication
- Lombok for reducing boilerplate code
- Maven for dependency management

## Getting Started

### Prerequisites

Before running this project, ensure you have:

- Java Development Kit (JDK) version 17 installed
- Maven installed
- MySQL database (for production setup)

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/mugosimon/security-application-embrack.git
   ```

2. Navigate to the project directory:

   ```bash
   cd security-application-embrack
   ```

3. Build the project using Maven:

   ```bash
   mvn clean package
   ```

### Running the Application

To run the application locally:

```bash
mvn spring-boot:run
```

By default, the application starts on port 8080.

## Configuration

- **Application Configuration**: Update `application.properties` or `application.yml` for database settings, JWT configuration, etc.
- **Security Configuration**: Customize security settings in `SecurityConfig.java`.

## Endpoints

- `/guys`: Accessible by users with roles USER or ADMIN.
- `/user`: Accessible by users with role USER.
- `/admin`: Accessible by users with role ADMIN.
- `/signIn`: POST endpoint for user authentication. Returns a JWT token on successful login.

## Testing

- Unit tests are included in the `src/test` directory. Run them using:

  ```bash
  mvn test
  ```

- Integration tests can be added to test API endpoints using tools like Postman or through automated scripts.

## License

This project is licensed under the MIT License. See the [LICENSE](./LICENSE) file for details.

## Dad Joke

Why don't skeletons fight each other? They don't have the guts.

---