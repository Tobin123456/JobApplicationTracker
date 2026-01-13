# Build project
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /application_tracker_backend
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Create lightweight image
FROM eclipse-temurin:21-jre
WORKDIR /application_tracker_backend
COPY --from=build /application_tracker_backend/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]