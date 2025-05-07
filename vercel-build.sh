#!/bin/bash
# Create a directory for tools
mkdir -p tools
cd tools

# Download and install OpenJDK 17
wget https://download.java.net/java/GA/jdk17.0.2/dfd4a8d0985749f896bed50d7138ee7f/8/GPL/ojdk17.0.2_linux-x64_bin.tar.gz
tar -xzf ojdk17.0.2_linux-x64_bin.tar.gz
export JAVA_HOME=$PWD/jdk-17.0.2
export PATH=$JAVA_HOME/bin:$PATH

# Download and install Maven
wget https://downloads.apache.org/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.tar.gz
tar -xzf apache-maven-3.9.9-bin.tar.gz
export PATH=$PWD/apache-maven-3.9.9/bin:$PATH

# Verify installations
java -version
mvn -version

# Run Maven to build the project
cd ..
mvn clean install -DskipTests