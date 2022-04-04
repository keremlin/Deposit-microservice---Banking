package com.tosan.deposit.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

@RestControllerAdvice
@Slf4j
public class HandleExceptions {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions2(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
            log.error(fieldName + " field " + errorMessage);
        });
        return errors;
    }

    @ExceptionHandler(NinNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNinNotFound(NinNotFoundException ex) {
        log.error(ex.getMessage());
        return ex.getMessage();
    }

    @ExceptionHandler(DepositNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleDepositNotFoundException(DepositNotFoundException ex) {
        log.error(ex.getMessage());
        return ex.getMessage();
    }

    @ExceptionHandler(DepositIsBlockException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleDepositIsBlockedException(DepositIsBlockException ex) {
        log.error(ex.getMessage());
        return ex.getMessage();
    }

    @ExceptionHandler(NotEnoughBalance.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleDepositIsBlockedException(NotEnoughBalance ex) {
        log.error(ex.getMessage());
        return ex.getMessage();
    }
    @ExceptionHandler(TransactionNotCommitted.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleTransactionNotCommitted(TransactionNotCommitted ex){
        log.error(ex.getMessage());
        return ex.getMessage();
    }
}
