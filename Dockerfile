# Create Gradle dependency cache so it isn't rebuilt every time
# Separating this from the main build makes things ~60% faster on my dev machine
# once the cache is built
FROM gradle:7.4.2-jdk18-alpine as dep-cache
WORKDIR /app/src
ENV GRADLE_USER_HOME /cache
COPY settings.gradle build.gradle ./
RUN gradle build --no-daemon > /dev/null 2>&1 || true

# Build using Gradle
FROM gradle:7.4.2-jdk18-alpine as build-image
COPY --from=dep-cache /cache /home/gradle/.gradle
COPY --chown=gradle:gradle . /app/src
WORKDIR /app/src
RUN gradle -g ./.gradle --stacktrace --no-daemon build

# Run application
FROM ibm-semeru-runtimes:open-18.0.1_10-jre
WORKDIR /app/bin
COPY --from=build-image /app/src/build/libs/*.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/bin/tech-assessment-0.0.1.jar"]