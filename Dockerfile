FROM openjdk:latest
MAINTAINER Arash
COPY target/deposit-0.0.1-SNAPSHOT.jar deposit.jar
ENTRYPOINT [ "java","-jar", "-Dspring.profiles.active=${DATASOURCE} -Dspring.profiles.active=${DBUNAME} -Dspring.profiles.active=${DBPASS}","/customer.jar" ]
