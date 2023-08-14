
FROM openjdk:18 AS build
COPY pom.xml pom.xml

RUN mvn clean install

FROM openjdk:18
WORKDIR /app
COPY target/credit-application-system.jar .
ENTRYPOINT ["java","-jar","credit-application-system.jar"]