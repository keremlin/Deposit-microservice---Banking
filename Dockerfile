FROM openjdk:latest
COPY target/deposit-0.0.1-SNAPSHOT.jar deposit.jar
ENTRYPOINT [ "java","-jar", "/deposit.jar" ]
