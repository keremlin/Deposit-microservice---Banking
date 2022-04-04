package com.tosan.deposit.exceptions;

public class DepositIsBlockException extends RuntimeException {
    public DepositIsBlockException (String message){
        super(message);
    }
    public DepositIsBlockException(){
        super();
    }
}
