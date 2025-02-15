FROM openjdk:21-jdk
COPY build/libs/api-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8087
ENTRYPOINT ["java", "-jar", "sports-tracker-app-1.0.jar"]
