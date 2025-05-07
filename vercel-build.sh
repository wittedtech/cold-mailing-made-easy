#!/bin/bash
# Install Java (OpenJDK 17) and Maven
apt-get update
apt-get install -y openjdk-17-jdk maven

# Run Maven to install dependencies and build
mvn clean install -DskipTests