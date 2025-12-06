FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY ./pom.xml /app/pom.xml
RUN mvn -N install

COPY ./auth-service/pom.xml /app/auth-service/pom.xml

WORKDIR /app/auth-service
RUN mvn dependency:go-offline

COPY ./auth-service/src /app/auth-service/src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/auth-service/target/*.jar /app/demo.jar
EXPOSE 8083
CMD ["java", "-jar", "demo.jar"]