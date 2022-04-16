# Deposit microservice - Tosan Banking=Loan project - Snapshot
There are Four microServices in this project: 
  - [Customer](https://github.com/keremlin/Customer-microservice---Tosan-Banking) : This project is providing customer-related services such as Registration.
  - [Deposit](https://github.com/keremlin/Deposit-microservice---Tosan-Banking) : This project aims to handle deposit-related works.
  - [Transactions](https://github.com/keremlin/Transactions-microservice---Tosan-Banking) : All transactions that happened in other services will be save using this service.
  - [Loans](https://github.com/keremlin/Loan-microservice---Tosan-Banking) : The project creates Loans and binds them to other services.
## Documents
### Compile and Run

For Running this project, you need to install JDK-11 and Maven. If you want to run application via Docker, you also need to install Docker on your test environment. The Database in this project is MySQL, but other databases could be used (maybe need configurations). 
After cloning the project from GitHub in the root of the project (where pom.xml is placed) run this command in the console : 
mvn clean package
The command will create .jar file in the 'target' folder. Then go to the 'target' folder and run: 
```bash
java -jar customer.jar
```
There is a Docker file in the root of the project, so you can build a container and just run it. The command is :
```bash
docker build -t customer-service:latest --build-arg DATASOURCE2=$DATASOURCE2 --build-arg DBUNAME=$DBUNAME --build-arg  DBPASS=$DBPASS .
```
And Run the Docker with connections string of the database and also provides related username and password of database, like this command:
```bash
docker run --env DATASOURCE2='jdbc:mysql://10.8.15.131:3307/customer' --env DBUNAME=root --env DBPASS=root -p 8080:8080 customer-service
```
Furthermore, there is a 'docker-compose.yml' at the project root. That bootstraps all modules that the project is needed, like Mysql, network, and the application. You should install Docker-composer. At the project root run these commands: 
```bash
docker-compose build
docker-compose up
```
That is it, the project is up and running and you can call REST services using port 8080. Do not forget to rebuild your package using Maven after making any changes in your code.
### Code
This project is based on Spring boot 2.6.4 and JDK 11. 
There are Six packages (Layers) in this project :
```bash
  ├───java
  │   └───com
  │       └───tosan
  │           └───deposit
  │               ├───controller
  │               ├───DTO
  │               ├───Exceptions
  │               ├───model
  │               ├───repositories
  │               └───services
  └───resources
```
The Layers have separate responsibilities based on [Solid principles](https://www.educative.io/edpresso/what-are-the-solid-principles-in-java).
All REST controllers are placed in the controller package. The connection between services is based on REST. The REST service is implemented by RESTfull standards(link).

The exceptions package is included by user-defined exceptions and also related handlers. Models and related validations are in the model package.

Models are protected by bean validation annotations, so data constrains are implemented by them. The exception handling of the bean validation is taking place in the RestControllerAdvice in Exception package in handleValidationExceptions.java file.

JPA repositories also are in its package. It also contains REST resources implementations. 

And provided services like customerService are in the service package.

### Logging
With Lombok's implementation of SLF4J, the logging process is done with @SF4j.

### Rest connections
The data exchange protocol between services is based on REST. As mentioned [here](https://spring.io/guides/tutorials/rest/), "building RESTful services" is done by Spring framework tools. So we use ResponseEntity on our controller side and use RestTemplate on the consumer side. For communication purposes, we separate RestTemplate layer repository layer. Thus if communication protocols are changed, only this layer will be changed.

### DTO Data Transfer Object layer
All data passed to the controllers should check by Bean validation. Therefore we use DTO to transfer data from clients. DTOs also have a convert method for converting to models. It uses BeanUtils.copyProperties for copying same name properties.
### Exceptions & Exception Handling
The project has a separate layer of exceptions. We have validation exceptions that invoke by DTOs and models. We also have some runtimeException like NinNotFoundException that is related to incorrect user inputs.
All exceptions handle by HandleValidationException and return appropriate HTTP code to the user if needed.
### Unit-Test Cases
For each service, we have appropriate test cases to ensure that the projects' parts work fine. The test cases are built using Junit and are mocked using Mockito.
     
