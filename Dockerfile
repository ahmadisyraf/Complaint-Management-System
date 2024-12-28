# Use the official OpenJDK image as the base image
FROM openjdk:17-jdk-slim

# Set the working directory (optional, but recommended for clarity)
WORKDIR /app

# Copy the JAR file from the build image to the current image
COPY target/demo-0.0.1-SNAPSHOT.jar /app/demo.jar

# Expose the port that your Spring Boot application runs on (default is 8081 as per your configuration)
EXPOSE 8081

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/demo.jar"]
