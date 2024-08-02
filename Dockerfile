FROM gradle:8.8.0-jdk21-alpine as builder

WORKDIR /app

COPY . /app/.

RUN chmod +x gradle

RUN gradle clean build -x test

FROM openjdk:21

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]