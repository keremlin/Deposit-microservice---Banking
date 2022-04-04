package com.tosan.deposit.dto;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    int id;

    @Min(1)
    @Max(Integer.MAX_VALUE)
    int transactionAmount;

    @NotNull
    TransactionStatus status;

    @NotNull
    TransactionType type;

    @Min(10000)
    @Max(99999)
    int source;

    @Min(10000)
    @Max(99999)
    int destination;

    String description = "";
}
