# =========================
# Credit Simulator - Dockerfile
# =========================

# Base image: OpenJDK 17 (Linux based)
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy built jar file into container
COPY target/credit_simulator-1.0-SNAPSHOT.jar /app/credit_simulator.jar

# Copy launcher script
COPY bin/credit_simulator /app/credit_simulator

# Make script executable
RUN chmod +x /app/credit_simulator

# Default entry point
ENTRYPOINT ["/app/credit_simulator"]

