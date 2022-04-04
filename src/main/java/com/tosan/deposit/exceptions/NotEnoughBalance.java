package com.tosan.deposit.exceptions;

public class NotEnoughBalance extends RuntimeException{
    public NotEnoughBalance(String message){
        super(message);
    }
    public NotEnoughBalance(){
        super();
    }
    
}
