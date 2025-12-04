FROM maven:3.9 AS build

ENV TZ=UTC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ENV CATALINA_OPTS="-Duser.timezone=UTC"

COPY ./pom.xml /devices-service/pom.xml
WORKDIR /devices-service
RUN mvn dependency:go-offline
COPY ./src/ /devices-service/src
WORKDIR /devices-service
RUN mvn clean install -DskipTests

FROM eclipse-temurin:21-jdk
WORKDIR /devices-service
COPY --from=build /devices-service/target/demo-0.0.1-SNAPSHOT.jar /devices-service/demo.jar
EXPOSE 8082
CMD ["java", "-jar", "demo.jar"]