# Use Java 21 JDK base image
FROM eclipse-temurin:21-jdk-alpine

# Set working directory inside the container
WORKDIR /app

# Copy the built JAR into the container
COPY target/hrms-0.0.1-SNAPSHOT.jar app.jar

# Expose port used by the Spring Boot app
EXPOSE 8080

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
