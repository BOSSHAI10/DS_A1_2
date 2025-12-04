FROM maven:3.9 AS build

ENV TZ=UTC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ENV CATALINA_OPTS="-Duser.timezone=UTC"

COPY ./pom.xml /users-service/pom.xml
WORKDIR /users-service
RUN mvn dependency:go-offline
COPY ./src/ /users-service/src
WORKDIR /users-service
RUN mvn clean install -DskipTests

FROM eclipse-temurin:21-jdk
WORKDIR /users-service
COPY --from=build /users-service/target/demo-0.0.1-SNAPSHOT.jar /users-service/demo.jar
EXPOSE 8081
CMD ["java", "-jar", "demo.jar"]
