## Application Build
FROM gradle:8.3-jdk21 AS builder
WORKDIR /home/gradle/app
COPY . /home/gradle/app
RUN gradle build --no-daemon

## Application Run
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

COPY --from=builder /home/gradle/app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]