
FROM maven:3-openjdk-17-slim
EXPOSE 8080
WORKDIR /backend
COPY src ./src
COPY pom.xml pom.xml
COPY .mvn/ .mvn
RUN mvn dependency:go-offline
ENTRYPOINT ["mvn", "spring-boot:run"]
