FROM openjdk:8-jre-slim
COPY build/libs/server.war /app/app.jar
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=prod", "app/app.jar"]
EXPOSE 3000