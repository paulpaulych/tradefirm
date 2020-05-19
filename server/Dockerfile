FROM openjdk:8-jre-slim

EXPOSE 3000

#RUN mkdir app

COPY ./build/libs/server-1.0-SNAPSHOT.war /app/app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]
