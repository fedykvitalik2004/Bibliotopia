# Use an official Maven 3.9.5 image as the base
FROM maven:3.9.9-amazoncorretto-21-debian-bookworm AS build
# Set the working directory to /app
WORKDIR /app
# Copy the current directory contents into the container at /app
COPY . /app/
# Build the Maven project
RUN mvn package -DskipTests -Dcheckstyle.skip
# Use an official OpenJDK 22-ea-21 image as the base
FROM openjdk:22-ea-21-jdk-slim
# Expose port 8080
EXPOSE 8080
# Set the working directory to /app
WORKDIR /app
# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar /app/
# Use the default command to run the Java application
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar *.jar"]