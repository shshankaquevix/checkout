FROM openjdk:8
ADD  /workspace/target/cart-checkout-service-0.0.1.jar app2.jar
EXPOSE 8093
ENTRYPOINT ["java", "-jar", "/app2.jar"]
