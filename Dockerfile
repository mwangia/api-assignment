FROM amazoncorretto:21.0.1

EXPOSE 9999

# Create a directory for the app
WORKDIR /app

# Copy the JAR into the container
COPY build/libs/assignment-0.0.1-SNAPSHOT.jar app.jar

# Run the app
ENTRYPOINT ["sh", "-c", "java -jar app.jar"]