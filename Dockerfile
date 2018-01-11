FROM openjdk:8
ADD  /var/lib/docker/tmp/docker-builder151687839/target/cart-checkout-service-0.0.1.jar  /
EXPOSE 8093
ENTRYPOINT ["java", "-jar", "/app2.jar"]
