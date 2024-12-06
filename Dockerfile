FROM maven:3.9.9-eclipse-temurin-23 AS builder

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

FROM eclipse-temurin:23-jre

WORKDIR /app

COPY --from=builder /app/LeWallServer/target/*-shaded.jar server.jar

EXPOSE 8559

ENTRYPOINT ["java", "-jar", "server.jar"]