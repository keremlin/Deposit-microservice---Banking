# Deposit microservice - Tosan Banking=Loan project
There are Four microServices in this project.
## Documents
### Code
This project is based on Spring boot 2.6.4 and JDK 11. 
There are Five packages in this project :

  ├───java
  │   └───com
  │       └───tosan
  │           └───customer
  │               ├───controller
  │               ├───DTO
  │               ├───Exceptions
  │               ├───model
  │               ├───repositories
  │               └───services
  └───resources

All rest controllers are placed in the controller package. The connection between services is based on REST. The REST service is implemented by RESTfull standards(link).

Exceptions package is included by user-defined exceptions and also related handlers. Models and related validations are in the model package.

Models are protected by bean validation annotations, so data constrains are implemented by them. The exception handling of the bean validation is take place in the RestControllerAdvice in Exception package in handleValidationExceptions.java file.

Jpa repositories also is in its package.It also contains Rest resources implementations. 

And provided services like customerService are in the service package.

### Logging
With Lombok implementation of SLF4J, the logging process is done with @SF4j.

### Rest connections
The data exchange protocol between services is based on REST. As mentioned [here](https://spring.io/guides/tutorials/rest/), "building RESTful services" is done by Spring framework tool sets. So we use ResponseEntity in our controller side and use RestTemplate in consumer side. For communication purposes, we separate RestTemplate layer repository layer.Thus if communication protocols is changed, only this layer will be changed.

### DTO Data Transfer Object layer
All data pass to the controllers should check by Bean validation. Therefore we use DTO to transfer data from clients. DTOs also have a convert method for converting to models. It uses BeanUtils.copyProperties for copying same name properties.
### Exceptions & Exception Handling
The projects has separated layer of exceptions. We have validation exceptions that invoke by DTOs and models. We also have some runtimeException like NinNotFoundException that is related to incorrect user inputs.
All exceptions handle by HandleValidationException and return appropriate HTTP code to user if needed.

     
