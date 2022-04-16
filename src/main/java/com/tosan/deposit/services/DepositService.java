package com.tosan.deposit.services;

import java.util.List;

import com.tosan.deposit.model.*;

public interface DepositService {

    public abstract Deposit createDeposit(String nin, int depositNumber, DepositType depositType, CurrencyType currencyType);

    public abstract List<Deposit> getListDeposit();

    public abstract Deposit changeDepositState(DepositState state, int nin);

    public List<Deposit> getListOfDepositOfCustomers(DepositType type);

    public int displayBalance(int depositNumber);

    public Deposit depositing(int depositNumber, int amount) ;

    public Deposit withdraw(int depositNumber, int amount);

    public List<Deposit> transfer(int from, int to, int amount);

    public List<Deposit> getAllDepositByNin(String nin);
    
    public boolean hasNinAnyDeposit(String nin);

    public Boolean isDepositNumberIsValid(int depositNumber);

    public Boolean withdrawInstallment(int depositNumber,int amount);
}
