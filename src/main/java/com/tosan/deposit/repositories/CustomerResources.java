package com.tosan.deposit.repositories;

import java.util.Optional;

import com.tosan.deposit.dto.CustomerDto;

public interface CustomerResources {
    public abstract Optional<CustomerDto> findCustomerByNin(String nin);
}
