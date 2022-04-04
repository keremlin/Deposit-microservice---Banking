package com.tosan.deposit.exceptions;

public class TransactionNotCommitted extends RuntimeException {

    public TransactionNotCommitted(){
        super();
    }
    public TransactionNotCommitted(String message){
        super(message);
    }
}
