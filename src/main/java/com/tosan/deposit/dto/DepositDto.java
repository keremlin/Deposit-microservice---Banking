package com.tosan.deposit.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.tosan.deposit.model.CurrencyType;
import com.tosan.deposit.model.Deposit;
import com.tosan.deposit.model.DepositState;
import com.tosan.deposit.model.DepositType;

import org.springframework.beans.BeanUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepositDto {
    
    int id;

    @NotBlank(message = "nin is mandatory on dto")
    @Pattern(regexp="^([0-9]){10}$",message = "Nin must have 10 digits only")
    @Size(min = 10,max = 10)
    String nin;

    @NotNull
    Integer depositNumber;

    int Balance=0;

    DepositState depositState;
    DepositType depositType;
    CurrencyType currencyType;
    
    Date startDate;
    Date endDate;

    public static <T>  T convertToEntity(DepositDto from,T to){
        BeanUtils.copyProperties(from, to);
        return to;
    }
    public static <T>  DepositDto convertToEntity(T from,DepositDto to){
        BeanUtils.copyProperties(from, to);
        return to;
    }
    public Deposit convertToEntity(){
        Deposit obj=new Deposit();
        BeanUtils.copyProperties(this, obj);
        return obj;
    }
    
}
