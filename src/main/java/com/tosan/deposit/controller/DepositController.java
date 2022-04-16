package com.tosan.deposit.controller;

import java.util.List;

import javax.validation.Valid;

import com.tosan.deposit.dto.*;
import com.tosan.deposit.model.Deposit;
import com.tosan.deposit.model.DepositType;
import com.tosan.deposit.services.DepositService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/deposit")
public class DepositController {

    @Autowired
    private DepositService depositService;

    @GetMapping("/{nin}/getAllDeposit")
    public ResponseEntity<List<Deposit>> getAllDepositByNin(@PathVariable String nin) {
        return ResponseEntity.ok().body(
            depositService.getAllDepositByNin(nin)
        ); 
    }

    @GetMapping("/{nin}/hasNinAnyDeposit")
    public ResponseEntity<Boolean> hasNinAnyDeposit(@PathVariable String nin){
        return ResponseEntity.ok().body(
            depositService.hasNinAnyDeposit(nin)
        );
    }

    @PostMapping("/createDeposit")
    public ResponseEntity<DepositDto> createDeposit(@Valid @RequestBody DepositDto deposit){
        DepositDto depositDto=new DepositDto();
        return ResponseEntity.ok().body(
         DepositDto.convertToEntity( 
             depositService.createDeposit(deposit.getNin(), deposit.getDepositNumber(),deposit.getDepositType(), deposit.getCurrencyType()),
             depositDto));
    }
    @GetMapping("/getListOfDeposit")
    public ResponseEntity<List<Deposit>> getListDeposit(){
        return ResponseEntity.ok().body(
            depositService.getListDeposit()
        );
    }
    @PostMapping("/changeDepositState")
    public ResponseEntity<Deposit> changeDepositState(@Valid @RequestBody ChangeDepositStateDto item){
        return ResponseEntity.ok().body(
            depositService.changeDepositState(item.getState(), item.getDepositNumber())
        );
    }
    @GetMapping("/{type}/getListOfDepositOfCustomers")
    public ResponseEntity<List<Deposit>> getListOfDepositOfCustomers(@PathVariable DepositType type){
        return ResponseEntity.ok().body(
            depositService.getListOfDepositOfCustomers(type)
        );
    }
   
    @GetMapping("/{depositNumber}/displayBalance")
    public ResponseEntity<Integer> displayBalance(@PathVariable int depositNumber) {
        return ResponseEntity.ok().body(
                depositService.displayBalance(depositNumber));
    }
    @PostMapping("/depositing")
    public ResponseEntity<Deposit> depositing(@Valid @RequestBody DepositActionsDto depositActionsDto ){
        return ResponseEntity.ok().body(
            depositService.depositing(depositActionsDto.getTo(),depositActionsDto.getAmount())
        );
    }
    @PostMapping("/withdraw")
    public ResponseEntity<Deposit> withdraw(@Valid @RequestBody DepositActionsDto depositActionDTO){
        return ResponseEntity.ok().body(
          depositService.withdraw(depositActionDTO.getFrom(), depositActionDTO.getAmount())
        );
    }
   
    @PostMapping("/transfer")
    public ResponseEntity<List<Deposit>> transfer(@Valid @RequestBody DepositActionsDto depositActionDto) {
        return ResponseEntity.ok().body(
                depositService.transfer(depositActionDto.getFrom(), depositActionDto.getTo(),
                        depositActionDto.getAmount()));
    }
    @GetMapping("/{depositNumber}/isValid")
    public ResponseEntity<Boolean> isDepositNumberValid(@PathVariable int depositNumber){
        return ResponseEntity.ok().body(
            depositService.isDepositNumberIsValid(depositNumber)
        );
    }
   
    @GetMapping("/{depositNumber}/{amount}/withdrawInstallment")
    public ResponseEntity<Boolean> withdrawInstallment(@PathVariable int depositNumber, @PathVariable int amount) {
        return ResponseEntity.ok().body(
                depositService.withdrawInstallment(depositNumber,amount));
    }
    
}
