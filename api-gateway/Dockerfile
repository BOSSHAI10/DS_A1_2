FROM maven:3.9 AS build

ENV TZ=UTC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ENV CATALINA_OPTS="-Duser.timezone=UTC"

COPY ./pom.xml /api-gateway/pom.xml
WORKDIR /api-gateway
RUN mvn dependency:go-offline
COPY ./src/ /api-gateway/src
WORKDIR /api-gateway
RUN mvn clean install -DskipTests

FROM eclipse-temurin:21-jdk
WORKDIR /api-gateway
COPY --from=build /api-gateway/target/demo-0.0.1-SNAPSHOT.jar /api-gateway/demo.jar
EXPOSE 8084
CMD ["java", "-jar", "demo.jar"]