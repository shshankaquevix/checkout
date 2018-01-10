FROM openjdk:8
ADD  ./target/*.jar app2.jar
EXPOSE 8093
ENTRYPOINT ["java", "-jar", "/app2.jar"]
