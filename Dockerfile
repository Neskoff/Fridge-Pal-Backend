FROM eclipse-temurin:21-jdk

# Set working directory inside the container
WORKDIR /app

# Copy the Gradle files first to leverage Docker cache
COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY gradle ./gradle

RUN ./gradlew dependencies --no-daemon

# Copy the source code
COPY src ./src

# Build the application
RUN ./gradlew build --no-daemon -x test

# Use the results of the build
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=0 /app/build/libs/*.jar app.jar

# Make port 8080 available outside the container
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
