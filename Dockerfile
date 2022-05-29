# Build using Gradle
FROM gradle:7.4.2-jdk18-alpine as build-image
COPY --chown=gradle:gradle . /app/src
WORKDIR /app/src
RUN gradle --stacktrace --no-daemon build

# Run application
FROM ibm-semeru-runtimes:open-18.0.1_10-jre
WORKDIR /app/bin
COPY --from=build-image /app/src/build/libs/*.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/bin/tech-assessment-0.0.1.jar"]