FROM gradle:6.8-jdk11 AS GRADLE_BUILD

COPY ./ ./

RUN gradle clean build

FROM openjdk:11-jre

COPY --from=GRADLE_BUILD /home/gradle/build/libs/json-validator-0.0.1-SNAPSHOT.jar /app.jar
COPY --from=GRADLE_BUILD /home/gradle/build/resources/main/schema.json /schema.json

CMD ["java", "-jar", "/app.jar"]