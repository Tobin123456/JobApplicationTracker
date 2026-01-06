# Application-Tracker Backend

A Spring Boot backend for persisting, managing, and tracking job applications. The project provides a enterprise-grade REST API with database persistence and built-in authentication mechanisms.

---

## Features

- CRUD operations for job applications
- Track application status (APPLIED, INTERVIEW, OFFER, REJECTED)
- Manage companies and jobs associated with applications
- Data persistence using **PostgreSQL**
- Authentication and authorization support:
    - **Basic Authentication** (via Spring Security)
    - **JWT Authentication** (token-based, via Spring Security + custom JWT implementation)

---

## Database

- PostgreSQL with Flyway-managed schema
- JPA/Hibernate for persistence and entity mapping

---

## Getting Started (Local Development)

**Prerequisites:**
- Java 21+
- PostgreSQL

**Setup:**
1. Set up the PostgreSQL database and automatically run migrations with Flyway:
 Ensure PostgreSQL is running and configure `application.properties`:
     - Example configuration for PostgreSQL:
       ```properties
       spring.datasource.url=jdbc:postgresql://localhost:5432/your_database
       spring.datasource.username=your_user
       spring.datasource.password=your_password
       ``` 
          
     - Define a secret key and expiration duration to authenticate users with Bearer JWT token:
       ```properties
       # MUST be at least 256 bits (32 chars) for HS256
       jwt.secret=MySuperSecretKeyForJWTs!
       jwt.expiration-ms=3600000
       ```
2. Run the Spring Boot backend with your IDE or via command line:
   ```bash
   ./mvnw spring-boot:run
   ```
3. Package the Application into a JAR
   ```bash
   ./mvnw clean package
   ```
4. Run the Packaged JAR
    ```bash
   java -jar target/Application_Tracker-0.0.1-SNAPSHOT.jar
   ```
---

## API Endpoints

The backend exposes server REST endpoints.
Here is a summary of the main backend endpoints: 

| Endpoint           | Method | Description                       | Auth                  |
|--------------------|--------|-----------------------------------|-----------------------|
| `api/applications` | GET    | Get all job applications           | Bearer JWT / Basic    |
| `api/applications` | POST   | Create a new application           | Bearer JWT / Basic    |
| `api/applications` | PATCH  | Update the status of an application| Bearer JWT / Basic    |
| `api/auth`         | POST   | Obtain JWT token using Basic Auth  | Basic                 |
| `api/users`        | POST   | Register a new user                | None                  |

### Notes
- **JWT (Bearer) Authentication**: Include the token in the request header as `Authorization: Bearer <token>`.
- **Basic Authentication**: Use your username and password encoded in Base64 in the `Authorization` header.
- If an endpoint supports both authentication methods, you can use **either** one to access it.
- Endpoint paths reflect the controller mappings — adjust them if your actual routes differ.
- This table covers core functionality. For full API details, see the OpenAPI / Swagger documentation:  
  [http://localhost:8080/swagger-ui/index.html#/application-controller](http://localhost:8080/swagger-ui/index.html#/application-controller)

## Testing

- Unit tests for services
- Controller tests for REST endpoints
- JPA Testing for persistence logic

---
