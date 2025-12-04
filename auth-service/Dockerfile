FROM maven:3.9 AS build

ENV TZ=UTC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ENV CATALINA_OPTS="-Duser.timezone=UTC"

COPY ./pom.xml /auth-service/pom.xml
WORKDIR /auth-service
RUN mvn dependency:go-offline
COPY ./src/ /auth-service/src
WORKDIR /auth-service
RUN mvn clean install -DskipTests

FROM eclipse-temurin:21-jdk
WORKDIR /auth-service
COPY --from=build /auth-service/target/demo-0.0.1-SNAPSHOT.jar /auth-service/demo.jar
EXPOSE 8083
CMD ["java", "-jar", "demo.jar"]