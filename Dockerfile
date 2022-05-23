FROM openjdk:19-jdk-alpine
COPY build/libs/wedding-game-0.0.1-SNAPSHOT.jar app.jar
COPY pdf pdf
ENTRYPOINT ["java","-jar","/app.jar"]
