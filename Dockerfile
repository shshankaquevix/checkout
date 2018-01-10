FROM openjdk:8
ADD  ./target/test /
EXPOSE 8093
ENTRYPOINT ["java", "-jar", "/app2.jar"]
