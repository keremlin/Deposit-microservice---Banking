package com.tosan.deposit.repositories;

import java.util.Optional;

import com.tosan.deposit.dto.CustomerDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerResourcesImpl implements CustomerResources {
    
    @Autowired
    private RestTemplate rest;

    @Value("${com.tosan.deposit}")
    private String depositURL;
    @Value("${com.tosan.customer}")
    private String customerURL;
    @Value("${com.tosan.transaction}")
    private String transactionURL;
    

    @Override
    public Optional<CustomerDto> findCustomerByNin(String nin) {
        try{
            return Optional.of( rest.getForObject("http://"+ customerURL +"/api/customer/"+nin+"/getCustomerByNin", CustomerDto.class));
        }catch(Exception ex){
            log.error(ex.getMessage());
            return Optional.empty();
        }
    }

}
