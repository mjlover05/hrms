## Use Java 21 JDK base image
#FROM eclipse-temurin:21-jdk-alpine
#
## Set working directory inside the container
#WORKDIR /app
#
## Copy the built JAR into the container
#COPY target/hrms-0.0.1-SNAPSHOT.jar app.jar
#
## Expose port used by the Spring Boot app
#EXPOSE 8080
#
## Run the JAR
#ENTRYPOINT ["java", "-jar", "app.jar"]
# STAGE 1 — Build the jar
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# STAGE 2 — Run the jar
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
