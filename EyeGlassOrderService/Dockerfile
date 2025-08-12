# ---- Build stage ----
FROM gradle:8.8-jdk21 AS build
WORKDIR /home/gradle/project
COPY . .
# Build the fat jar (skip tests for speed during container build)
RUN gradle clean bootJar -x test --no-daemon

# ---- Run stage ----
FROM eclipse-temurin:21-jre
WORKDIR /app
# Adjust the glob if your jar name differs
COPY --from=build /home/gradle/project/build/libs/*-SNAPSHOT.jar /app/app.jar

# JVM & Spring profile (set a docker profile if you have application-docker.yml)
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75"
# If you created a docker profile, uncomment the next line:
# ENV SPRING_PROFILES_ACTIVE=docker

EXPOSE 8088
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]