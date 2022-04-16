package com.tosan.deposit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.tosan.deposit.dto.DepositDto;
import com.tosan.deposit.model.CurrencyType;
import com.tosan.deposit.model.Deposit;
import com.tosan.deposit.model.DepositState;
import com.tosan.deposit.model.DepositType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConvertModelToDtoTest {
    Deposit deposit;

    @BeforeEach
    void beforeAll() {
        deposit = new Deposit(
                0,
                "3875568255",
                123,
                0,
                DepositState.OPEN,
                DepositType.LONG_TERM,
                CurrencyType.RIAL,
                null,
                null);
    }

    @Test
    void checkCopyProperties() {
        DepositDto testCase=new DepositDto();
        DepositDto.convertToEntity(deposit,testCase );
        assertEquals(deposit.getNin(),testCase.getNin());
        assertEquals(deposit.getDepositNumber(),testCase.getDepositNumber());
        assertEquals(deposit.getCurrencyType(),testCase.getCurrencyType());
    }
    
    @Value("${com.tosan.deposit}")
    private String depositURL;
    @Value("${com.tosan.customer}")
    private String customerURL;
    @Value("${com.tosan.transaction}")
    private String transactionURL;
    @Test
    void checkPropertiesValues(){
        assertTrue(customerURL.length()>0);
        assertTrue(depositURL.length()>0);
        assertTrue(transactionURL.length()>0);
    }
}
