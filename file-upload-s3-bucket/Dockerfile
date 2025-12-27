# Use a base image with Java 17 JRE
FROM eclipse-temurin:17-jre-jammy

# Set the working directory inside the container
WORKDIR /app

# Argument to specify the JAR file path.
# This makes it easier to specify the jar file at build time if needed.
ARG JAR_FILE=target/*.jar

# Copy the executable JAR file from the host to the container
COPY ${JAR_FILE} app.jar

# Expose the port the application will run on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
