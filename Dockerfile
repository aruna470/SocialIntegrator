FROM eclipse-temurin:11-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY src/main/resources/* /
RUN mkdir -p logs
ENTRYPOINT ["java", "-Dlogging.config=log4j2.xml", "-jar", "/app.jar"]