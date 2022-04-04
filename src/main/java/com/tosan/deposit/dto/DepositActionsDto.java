package com.tosan.deposit.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DepositActionsDto {

    @Min(1)
    @Max(Integer.MAX_VALUE)
    int amount;

    @Min(10000)
    @Max(99999)
    int from;

    @Min(10000)
    @Max(99999)
    int to;
}
