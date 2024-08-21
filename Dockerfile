FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app

COPY ./pom.xml .
RUN mvn dependency:go-offline

COPY src src
RUN mvn package -DskipTests
RUN java -Djarmode=layertools -jar target/projeto-integrado-iv-api.jar extract

FROM eclipse-temurin:21-alpine
RUN addgroup -S apprunner && adduser -S apprunner -G apprunner
USER apprunner
EXPOSE 8080

WORKDIR /app

COPY --from=builder app/dependencies/ ./
COPY --from=builder app/spring-boot-loader/ ./
COPY --from=builder app/snapshot-dependencies/ ./
COPY --from=builder app/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]