# Multi-stage Dockerfile for building and running the Spring Boot application
# Build stage - uses Maven to compile and package the app
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /workspace

# Copy pom and download dependencies (better layer caching)
COPY pom.xml ./
RUN mvn -B -e -q dependency:go-offline

# Copy source and build
COPY src ./src
RUN mvn -B -e -q package -DskipTests

# Runtime stage - minimal JRE image
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy jar from build stage (Spring Boot repackage creates executable jar)
ARG JAR_FILE=target/gym-admin-backend-1.0.0.jar
COPY --from=build /workspace/${JAR_FILE} app.jar

# Expose default Spring Boot port
EXPOSE 8080

# Use a non-root user for better security (optional)
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

ENTRYPOINT ["sh", "-c", "java -jar /app/app.jar"]
