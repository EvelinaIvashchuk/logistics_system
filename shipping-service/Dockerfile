# Stage 1: Build
FROM gradle:8.7-jdk21 AS build
WORKDIR /home/gradle/src
COPY --chown=gradle:gradle . .
RUN ./gradlew :shipping-service:bootJar --no-daemon

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /home/gradle/src/shipping-service/build/libs/shipping-service-1.0-SNAPSHOT.jar app.jar
EXPOSE 8082
EXPOSE 9092
ENTRYPOINT ["java", "-jar", "app.jar"]
