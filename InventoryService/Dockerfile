# ------------ Build stage ------------
FROM gradle:8.8-jdk21 AS build
WORKDIR /home/gradle/project
COPY . .
# Build the fat jar (skip tests during image build for speed)
RUN gradle clean bootJar -x test --no-daemon

# ------------ Run stage -------------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the built jar (adjust the glob if your jar name differs)
COPY --from=build /home/gradle/project/build/libs/*-SNAPSHOT.jar /app/app.jar

# Sensible defaults
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75"
# Use your docker profile so the container uses envs & container DNS (postgres/kafka/discovery-server)
ENV SPRING_PROFILES_ACTIVE=docker

# Default ports
EXPOSE 8088

# Optional: quick liveness check (useful in k8s/compose healthchecks)
HEALTHCHECK --interval=20s --timeout=3s --retries=10 CMD wget -qO- http://localhost:8088/actuator/health || exit 1

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]