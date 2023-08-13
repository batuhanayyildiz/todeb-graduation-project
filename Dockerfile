FROM openjdk:18 AS build

COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package

FROM openjdk:18
WORKDIR creditApplicationSystem
COPY --from=build target/*.jar credit-applicadocketion-system.jar
ENTRYPOINT ["java", "-jar", "credit-application-system.jar"]