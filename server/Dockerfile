FROM openjdk:8
COPY build/libs/server.jar /app/app.jar
ENTRYPOINT java -jar app/app.jar --spring.profiles.active=prod