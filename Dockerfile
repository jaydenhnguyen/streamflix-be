FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the entire project
COPY . .

# Build the JAR
RUN ./mvnw clean package -DskipTests && cp target/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the app
CMD ["java", "-jar", "app.jar"]
