FROM maven:3.8.5-openjdk-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src src
RUN ["mvn", "package"]
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY entrypoint.sh /entrypoint.sh
COPY --from=builder /app/target/rbc-library-rental-0.0.1-SNAPSHOT.jar rbc-library-rental-0.0.1-SNAPSHOT.jar
EXPOSE 8080
RUN chmod +x /entrypoint.sh
RUN adduser -u 1001 -D non-root-user && chown -R non-root-user:non-root-user /app
USER non-root-user
ENTRYPOINT ["/entrypoint.sh"]