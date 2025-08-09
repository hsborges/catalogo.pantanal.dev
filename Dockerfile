# syntax=docker/dockerfile:1.7

# -------- Build stage --------
FROM maven:3.9-eclipse-temurin-17-alpine AS build

WORKDIR /workspace

# Copy only descriptor first to leverage layer caching for dependencies
COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2 \
    mvn -B -ntp -DskipTests dependency:go-offline

# Copy sources and build
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 \
    mvn -B -ntp -DskipTests clean package

# -------- Runtime stage --------
FROM eclipse-temurin:17-jre-alpine

# Create non-root user
RUN addgroup -S spring && adduser -S spring -G spring
WORKDIR /app

# Copy fat jar from build stage
COPY --from=build /workspace/target/*.jar /app/app.jar

# Expose default Spring Boot port
EXPOSE 8080

# Run as non-root
USER spring

# JVM tuning for containers
ENTRYPOINT ["java","-XX:MaxRAMPercentage=75.0","-XX:+UseG1GC","-Djava.security.egd=file:/dev/./urandom","-jar","/app/app.jar"]
