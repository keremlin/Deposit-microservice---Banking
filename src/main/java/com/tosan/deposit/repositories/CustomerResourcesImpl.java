package com.tosan.deposit.repositories;

import java.util.Optional;

import com.tosan.deposit.dto.CustomerDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerResourcesImpl implements CustomerResources {
    
    @Autowired
    private RestTemplate rest;

    
    

    @Override
    public Optional<CustomerDto> findCustomerByNin(String nin) {
        try{
            return Optional.of( rest.getForObject("http://localhost:8080/api/customer/"+nin+"/getCustomerByNin", CustomerDto.class));
        }catch(Exception ex){
            log.error(ex.getMessage());
            return Optional.empty();
        }
    }

}
