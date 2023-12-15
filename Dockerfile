FROM openjdk:17-oracle

COPY build/libs/ContaclsWebDB-0.0.1-SNAPSHOT.jar contact-app.jar

CMD ["java", "-jar", "contact-app.jar"]