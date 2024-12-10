This is a backend service developed using Spring Boot, Gradle, and an H2 Database. 
It demonstrates a basic CRUD application with REST APIs for managing entities like Lecturer and Student, following clean architecture principles. 
The application supports containerized deployment using Docker Compose.

Features
REST APIs for:
- Creating and fetching Lecturer and Student entities.
- Assigning students to lecturers.
- Validation for input fields.
- Integration tests using H2 in-memory database.
- Containerized with Docker for simplified deployment.
Requirements
Ensure the following are installed on your system:
- Java 21
- Gradle (wrapper included)
- Docker and Docker Compose

Run Locally
   Without Docker:
   Build the project:
```
./gradlew clean build
```
```
./gradlew bootRun
```
 
Access the application at http://localhost:8080.

```
docker-compose up --build
```

```
docker-compose down
```