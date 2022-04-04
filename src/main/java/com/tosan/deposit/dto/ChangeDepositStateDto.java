package com.tosan.deposit.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.tosan.deposit.model.DepositState;

import lombok.Data;

@Data
public class ChangeDepositStateDto {
    
    @Min(10000)
    @Max(99999)
    int depositNumber;
    
    @NotNull
    DepositState state;
}
