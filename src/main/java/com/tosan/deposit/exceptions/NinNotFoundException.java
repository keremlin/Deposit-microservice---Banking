package com.tosan.deposit.exceptions;

public class NinNotFoundException extends RuntimeException {
    public NinNotFoundException(String message){
        super(message);
    }
    public NinNotFoundException(){
        super();
    }
}
