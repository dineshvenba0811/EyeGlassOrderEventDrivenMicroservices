# ---- Build stage ----
FROM gradle:8.8-jdk21 AS build
WORKDIR /home/gradle/project
COPY . .
RUN gradle clean bootJar -x test --no-daemon

# ---- Run stage ----
FROM eclipse-temurin:21-jre
WORKDIR /app

# copy the fat jar (adjust the glob if your jar name differs)
COPY --from=build /home/gradle/project/build/libs/*-SNAPSHOT.jar /app/app.jar

ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75"
ENV SPRING_PROFILES_ACTIVE=docker

EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]