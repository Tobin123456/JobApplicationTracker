# Application-Tracker Backend

The **Application-Tracker Backend** is a Spring Boot microservice for persisting, managing, and tracking job applications.  
It provides an enterprise-grade REST API with database persistence and built-in authentication mechanisms (JWT & Basic Auth).

---

## Table of Contents

- [Features](#features)
- [Architecture](#architecture)
- [Environment Variables](#environment-variables)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Testing](#testing)
- [License](#license)

---

## Features

- CRUD operations for managing job applications, jobs and companies
- Track application status (APPLIED, INTERVIEW, OFFER, REJECTED)
- Data persistence using **PostgreSQL**
- Authentication and authorization support:
    - **Basic Authentication** (via Spring Security)
    - **JWT Authentication** (token-based, via Spring Security + custom JWT implementation)

---

## Architecture

- Spring Boot backend using **Java 21+**
- **PostgreSQL** as the primary database
- **Flyway** for schema migrations
- **JPA/Hibernate** for entity mapping and persistence
- REST API layer with Spring MVC controllers
- Security layer with Spring Security for JWT & Basic Auth

> Note: No separate diagram is provided, but modules follow a typical layered architecture:  
> `Controller → Service → Repository → Database`

---

## Environment Variables

The Application-Tracker Backend is fully configurable at runtime using **environment variables**.  
No edits to `application.properties` are necessary. Spring Boot will automatically resolve the `${...}` placeholders.

### PostgreSQL Database Configuration

| Variable                     | Description                          | Example                                                |
|------------------------------|--------------------------------------|--------------------------------------------------------|
| `SPRING_DATASOURCE_URL`      | JDBC URL for the PostgreSQL database | `jdbc:postgresql://localhost:5432/application_tracker` |
| `SPRING_DATASOURCE_USERNAME` | Database username                    | `postgres`                                             |
| `SPRING_DATASOURCE_PASSWORD` | Database password                    | `password`                                             |

### JWT Authentication Configuration

| Variable         | Description                                                                  | Example                    |
|------------------|------------------------------------------------------------------------------|----------------------------|
| `JWT_SECRET_KEY` | Secret key for signing JWTs (must be at least 256 bits / 32 chars for HS256) | `MySuperSecretKeyForJWTs!` |

### CORS Configuration

| Variable               | Description                                      | Example                                    |
|------------------------|--------------------------------------------------|--------------------------------------------|
| `CORS_ALLOWED_ORIGINS` | Comma-separated list of allowed frontend origins | `http://localhost:3000,http://example.com` |

### How to Set Environment Variables

- **Deploying via Docker**: Environment variables are configured in the `docker-compose.yml` file. See [Docker Deployment](#docker-deployment-full-stack) for details.
- **Deploying without Docker**: Pass environment variables as command-line arguments when running the JAR. See [Local Deployment](#local-development-standalone) for details.

---

## Getting Started

The Application-Tracker Backend can be run in **two ways**:

1. **Local Development (Standalone)** – backend only, user provides their own PostgreSQL database
2. **Docker Deployment (Full Stack)** – backend, database, and frontend together via Docker

Choose the method that best fits your workflow.

### Local Development Standalone

**Prerequisites:**

- Java 21+
- PostgreSQL (must be set up separately by the user)
- Environment variables configured (see [Environment Variables](#environment-variables))

**Setup:**

1. Ensure your PostgreSQL database is running
2. Package the Application into a JAR
   ```bash
   ./mvnw clean package -DskipTests
   ```
3. Run the Packaged JAR, example:
    ```bash
   java -jar target/Application_Tracker-1.0.0.jar `
   --SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/application_data `
   --SPRING_DATASOURCE_USERNAME=job_searcher `
   --SPRING_DATASOURCE_PASSWORD=231198 `
   --JWT_SECRET_KEY="MySuperSecretKeyForJWTsThe_Factory_Must_Grow!" `
   --CORS_ALLOWED_ORIGINS=http://localhost:4200
   ```

---

### Docker Deployment Full Stack

The project can be deployed together with the frontend by following the instruction in the deployment [project](https://github.com/Tobin123456/Job-Application-Tracker).

- If you want to use the backend as-is, no further action is needed.
- If you have branched this backend or made changes, you need to **push your updated image** to your own GitHub Container Registry and update the deployment [docker-compose](https://github.com/Tobin123456/Job-Application-Tracker/blob/main/docker-compose.yml) in the deployment project to use your own registry.

**Steps to deploy your own backend image:**
1. Login in your GitHub Container Registry with your personal access token (if not already done)
   ```bash
   echo <YOUR_GITHUB_TOKEN> | docker login ghcr.io -u <YOUR_GITHUB_USERNAME> --password-stdin
   ```
2. Build your updated docker image
   ```bash
   docker build -t ghcr.io/<YOUR_GITHUB_USERNAME>/application-tracker-backend:1.0 .
   ```
3. Push your image to your GitHub Container Registry
   ```bash
   docker push ghcr.io/<YOUR_GITHUB_USERNAME>/application-tracker-backend:1.0
   ```
4. Update the [docker-compose](https://github.com/Tobin123456/Job-Application-Tracker/blob/main/docker-compose.yml) to reference your own registry. For more information see this [README](https://github.com/Tobin123456/Job-Application-Tracker/blob/main/README.md)

---

## API Endpoints

The backend exposes server REST endpoints.
Here is a summary of the main backend endpoints: 

| Endpoint                       | Method | Description                         | Auth               |
|--------------------------------|--------|-------------------------------------|--------------------|
| `api/applications`             | GET    | Get all job applications            | Bearer JWT / Basic |
| `api/applications`             | POST   | Create a new application            | Bearer JWT / Basic |
| `api/applications/{id]/status` | PATCH  | Update the status of an application | Bearer JWT / Basic |
| `api/auth`                     | POST   | Obtain JWT token using Basic Auth   | Basic              |
| `api/users`                    | POST   | Register a new user                 | None               |

### Notes
- **JWT (Bearer) Authentication**: Include the token in the request header as `Authorization: Bearer <token>`.
- **Basic Authentication**: Use your username and password encoded in Base64 in the `Authorization` header.
- If an endpoint supports both authentication methods, you can use **either** one to access it.
- Endpoint paths reflect the controller mappings — adjust them if your actual routes differ.
- This table covers core functionality. For full API details, see the OpenAPI / Swagger documentation after application start:  
  [http://localhost:8080/swagger-ui/index.html#/application-controller](http://localhost:8080/swagger-ui/index.html#/application-controller)

---

## Testing

The Application-Tracker Backend includes automated tests to ensure reliability and correctness.

### Types of Tests

- **Unit Tests** – for service layer and business logic
- **Controller Tests** – for REST endpoints and request/response handling
- **JPA / Persistence Tests** – for database interactions and entity mapping

### Running Tests
**Prerequisites:**

- A running docker environment as the JPA tests utilize test containers

**Run all tests using Maven::**

```bash
./mvnw test
```

---

## License

This project is licensed under the **MIT License**.

See the full license text in the [LICENSE](./LICENSE) file.
