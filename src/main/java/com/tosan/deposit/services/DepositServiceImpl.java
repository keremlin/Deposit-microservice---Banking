package com.tosan.deposit.services;

import com.tosan.deposit.dto.CustomerDto;
import com.tosan.deposit.dto.TransactionDto;
import com.tosan.deposit.dto.TransactionType;
import com.tosan.deposit.dto.TransactionStatus;
import com.tosan.deposit.exceptions.*;
import com.tosan.deposit.model.CurrencyType;
import com.tosan.deposit.model.Deposit;
import com.tosan.deposit.model.DepositState;
import com.tosan.deposit.model.DepositType;
import com.tosan.deposit.repositories.CustomerResources;
import com.tosan.deposit.repositories.DepositRepository;
import com.tosan.deposit.repositories.TransactionsResources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

@Service
@Validated
public class DepositServiceImpl implements DepositService {

    @Autowired
    private CustomerResources customerResource;
    @Autowired
    private DepositRepository repo;
    @Autowired
    private TransactionsResources transactionResource;

    @Override
    @Transactional
    public Deposit createDeposit(String nin, int depositNumber, DepositType depositType, CurrencyType currencyType) {
        Optional<CustomerDto> customer = customerResource.findCustomerByNin(nin);
        if (customer.isPresent()) {
            Deposit deposit = new Deposit(
                    0,
                    nin,
                    depositNumber,
                    0,
                    DepositState.OPEN,
                    depositType,
                    currencyType,
                    new Date(),
                    new Date());
            return repo.save(deposit);
        } else {
            throw new NinNotFoundException("Customer with nin : " + nin + " not exists.");
        }
    }

    @Override
    public List<Deposit> getListDeposit() {
        return repo.findAll();
    }

    @Override
    @Transactional
    public Deposit changeDepositState(DepositState state, int depositNumber) {
        Deposit deposit = repo.findByDepositNumber(depositNumber)
                .orElseThrow(() -> new DepositNotFoundException(depositNumber+""));
        if(deposit.getDepositState()==DepositState.BLOCK){
            throw new DepositIsBlockException("The Deposit is Blocked and it can not be changed with number : "+depositNumber);
        }

        deposit.setDepositState(state);
        deposit = repo.save(deposit);
        return deposit;
    }
    @Override
    public List<Deposit> getListOfDepositOfCustomers(DepositType type){
        return repo.findByDepositType(type);
    }
    @Override
    public int displayBalance(int depositNumber) {
        return repo.findByDepositNumber(depositNumber)
                .orElseThrow(() -> new DepositNotFoundException(depositNumber + ""))
                .getBalance();
    }
   
    @Override
    @Transactional
    public Deposit depositing(int depositNumber, int amount) {
        Deposit tempDeposit = repo.findByDepositNumber(depositNumber)
                .orElseThrow(() -> new DepositNotFoundException(depositNumber + ""));
        if (tempDeposit.getDepositState() == DepositState.BLOCK ||
                tempDeposit.getDepositState() == DepositState.BLOCK_DEPOSIT) {
            throw new DepositIsBlockException("The deposit is block : " + depositNumber);
        }
        tempDeposit.setBalance(tempDeposit.getBalance() + amount);
        if (transactionResource.registerTransaction(new TransactionDto(0, amount,
                TransactionStatus.successful, TransactionType.deposit, depositNumber, depositNumber, " ")).isEmpty())
            throw new TransactionNotCommitted("The Transaction server is not reachable");

        return repo.save(tempDeposit);
    }

    @Override
    @Transactional
    public Deposit withdraw(int depositNumber, int amount) {
        Deposit tempDeposit = repo.findByDepositNumber(depositNumber)
                .orElseThrow(() -> new DepositNotFoundException(depositNumber + ""));
        tempDeposit.setBalance(tempDeposit.getBalance() - amount);
        if (tempDeposit.getDepositState() == DepositState.BLOCK ||
                tempDeposit.getDepositState() == DepositState.BLOCK_WITHDRAW) {
            throw new DepositIsBlockException("The deposit is block : " + depositNumber);
        } else if (tempDeposit.getBalance() < 0) {
            throw new NotEnoughBalance("There is not Enough balance in : " + depositNumber);
        } else {
            if (transactionResource.registerTransaction(new TransactionDto(0, amount,
                    TransactionStatus.successful, TransactionType.withdraw, depositNumber, depositNumber, " "))
                    .isEmpty())
                throw new TransactionNotCommitted("The Transaction server is not reachable");
            return repo.save(tempDeposit);
        }

    }

    @Override
    @Transactional
    public List<Deposit> transfer(int from, int to, int amount) {
        List<Deposit> list = new ArrayList<>();

        Deposit tempDepositTo = repo.findByDepositNumber(to)
                .orElseThrow(() -> new DepositNotFoundException(to + ""));
        Deposit tempDepositFrom = repo.findByDepositNumber(from)
                .orElseThrow(() -> new DepositNotFoundException(from + ""));

        tempDepositFrom.setBalance(tempDepositFrom.getBalance() - amount);
        tempDepositTo.setBalance(tempDepositTo.getBalance() + amount);

        if (tempDepositTo.getDepositState() == DepositState.BLOCK ||
                tempDepositFrom.getDepositState() == DepositState.BLOCK ||
                tempDepositTo.getDepositState() == DepositState.BLOCK_DEPOSIT ||
                tempDepositFrom.getDepositState() == DepositState.BLOCK_WITHDRAW) {
            throw new DepositIsBlockException("Deposit is blocked : " + from + " or " + to);
        } else if (tempDepositFrom.getBalance() < 0) {
            throw new NotEnoughBalance("There is not Enough balance in : " + from);
        }
        if (transactionResource.registerTransaction(new TransactionDto(0, amount,
                TransactionStatus.successful, TransactionType.transfer, from, to, " ")).isEmpty())
            throw new TransactionNotCommitted("The Transaction server is not reachable");
        list.add(repo.save(tempDepositTo));
        list.add(repo.save(tempDepositFrom));
        return list;
    }
    
    @Override
    public List<Deposit> getAllDepositByNin(String nin){
        return repo.findByNin(nin);
    }
    @Override
    public boolean hasNinAnyDeposit(String nin){
        if(repo.countByNin(nin)>0)
            return true;
        else
            return false;
    }
    @Override
    public Boolean isDepositNumberIsValid(int depositNumber){
        return repo.findByDepositNumber(depositNumber).isPresent();
    }
    @Override
    public Boolean withdrawInstallment(int depositNumber,int amount){
        withdraw(depositNumber, amount);
        return true;
    }


    
    
}
