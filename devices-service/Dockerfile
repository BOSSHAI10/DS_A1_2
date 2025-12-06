FROM maven:3.9 AS build
WORKDIR /app

COPY ./pom.xml /app/pom.xml
RUN mvn -N install

COPY ./devices-service/pom.xml /app/devices-service/pom.xml

WORKDIR /app/devices-service
RUN mvn dependency:go-offline

COPY ./devices-service/src /app/devices-service/src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/devices-service/target/*.jar /app/demo.jar
EXPOSE 8082
CMD ["java", "-jar", "demo.jar"]