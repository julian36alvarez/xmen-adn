# Use an official OpenJDK runtime as a parent image
FROM openjdk:11-jre-slim

# Set the working directory in the container
WORKDIR /app

# Copy the build output from the host to the container
COPY microservicio/build/libs/*.jar app.jar

# Expose the port the application runs on
EXPOSE 9000

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]