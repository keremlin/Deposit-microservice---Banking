package com.tosan.deposit.repositories;

import java.util.List;
import java.util.Optional;

import com.tosan.deposit.model.Deposit;
import com.tosan.deposit.model.DepositType;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositRepository extends CrudRepository<Deposit, Integer> {

    List<Deposit> findAll();

    Optional<Deposit> findByDepositNumber(int depositNumber);

    List<Deposit> findByDepositType(DepositType type);

    List<Deposit> findByNin(String nin);

    Integer countByNin(String nin);

}
