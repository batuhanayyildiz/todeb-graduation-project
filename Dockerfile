FROM openjdk:18 AS build

COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package

FROM openjdk:18
WORKDIR credit-application-system
COPY --from=build target/*.jar CreditApplicationSystemApplication.jar
ENTRYPOINT ["java", "-jar", "CreditApplicationSystemApplication.jar"]