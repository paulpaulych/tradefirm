FROM openjdk:8-jre-slim
COPY ./build/libs/server-1.0-SNAPSHOT.war /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
EXPOSE 3000