FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# 1. Copiem si instalam parintele (din root)
COPY ./pom.xml /app/pom.xml
RUN mvn -N install

# 2. Copiem pom-ul serviciului
COPY ./users-service/pom.xml /app/users-service/pom.xml

# 3. Pregatim dependintele
WORKDIR /app/users-service
RUN mvn dependency:go-offline

# 4. Copiem sursele (Aici era eroarea: specificam calea completa de la root)
COPY ./users-service/src /app/users-service/src

# 5. Build
RUN mvn clean package -DskipTests

# --- Runtime Stage ---
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/users-service/target/*.jar /app/demo.jar
EXPOSE 8081
CMD ["java", "-jar", "demo.jar"]