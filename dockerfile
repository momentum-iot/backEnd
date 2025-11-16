#
# Multi-stage build for Gym Admin Backend (Spring Boot 3)
#

# --- Build stage ---
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom first to leverage layer caching
COPY pom.xml .
RUN mvn -ntp -B dependency:go-offline

# Copy source and build the jar
COPY src ./src
RUN mvn -ntp -B clean package -DskipTests

# --- Runtime stage ---
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Env defaults (override as needed)

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]
