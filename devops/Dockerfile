FROM openjdk:13-ea-jdk-alpine
VOLUME /tmp
COPY build/libs/wallet-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
