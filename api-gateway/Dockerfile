FROM maven:3.9 AS build
WORKDIR /app

COPY ./pom.xml /app/pom.xml
RUN mvn -N install

COPY ./api-gateway/pom.xml /app/api-gateway/pom.xml

WORKDIR /app/api-gateway
RUN mvn dependency:go-offline

COPY ./api-gateway/src /app/api-gateway/src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/api-gateway/target/*.jar /app/demo.jar
EXPOSE 8084
CMD ["java", "-jar", "demo.jar"]