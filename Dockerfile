# Stage 1: Build the application
FROM maven:3.9.9-eclipse-temurin-17 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml file first to leverage Docker caching
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline

# Now copy the rest of the application
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:11-jdk

# Set the working directory
WORKDIR /app

# Copy the built jar file from the previous stage
COPY --from=build /app/target/bankAPI-0.0.1-SNAPSHOT.jar bankAPI.jar

# Run the application
ENTRYPOINT ["java", "-jar", "bankAPI.jar"]
