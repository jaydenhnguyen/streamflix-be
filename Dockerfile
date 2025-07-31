# Use Java 17 base image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the entire project
COPY . .

# Build the JAR using Maven
RUN ./mvnw clean package -DskipTests

# Expose Spring Boot port
EXPOSE 3000

# Run the app
CMD ["java", "-jar", "target/*.jar"]
