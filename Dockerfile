FROM openjdk:17-oracle

COPY build/libs/custodial_network-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 9286

ENTRYPOINT ["java", "-jar", "/app.jar"]