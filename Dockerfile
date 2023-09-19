FROM eclipse-temurin:17-jdk-alpine
LABEL authors="a383784"
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar","--spring.profiles.active=postgres"]
#"--spring.profiles.active=postgres"