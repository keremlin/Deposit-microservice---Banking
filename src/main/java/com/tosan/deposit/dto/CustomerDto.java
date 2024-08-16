package com.tosan.deposit.dto;
import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    

    int id;
    
    @NotBlank(message = "nin is mandatory")
    @Pattern(regexp="^([0-9]){10}$",message = "Nin must have 10 digits only")
    @Size(min = 10,max = 10)
    String nin;

    @NotBlank
    @Size(min=3)
    String name;

    @NotBlank
    @Size(min=3)
    String family;
    
    @NotBlank
    String code;

    @NotBlank
    @Size(max=10,min=10)
    String mobilePhone;

    Date birthDate;  
    Date createdDate;

    
}

