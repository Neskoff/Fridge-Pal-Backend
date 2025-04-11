FROM eclipse-temurin:21-jdk

# Set working directory inside the container
WORKDIR /app

# Copy the Gradle/Maven files first to leverage Docker cache
COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY gradle ./gradle

# Download dependencies (this step gets cached unless build files change)
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

# Set environment variables if needed
ENV SPRING_PROFILES_ACTIVE=production

# Run the application
CMD ["java", "-jar", "app.jar"]