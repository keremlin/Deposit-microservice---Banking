package com.tosan.deposit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.tosan.deposit.dto.*;
import com.tosan.deposit.exceptions.*;
import com.tosan.deposit.model.*;
import com.tosan.deposit.repositories.*;
import com.tosan.deposit.services.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DepositApplicationTests {

	@InjectMocks
	private DepositServiceImpl service;

	@Mock
	private CustomerResourcesImpl customerResource;

	@Mock
	private DepositRepository repo;

	@Mock
	private TransactionsResources transactionResource;

	Deposit deposit;
	Deposit depositFrom;
	Deposit depositTo;

	@BeforeEach
	void beforeEach() {

		deposit = new Deposit(
				0,
				"3875568255",
				123,
				100,
				DepositState.OPEN,
				DepositType.LONG_TERM,
				CurrencyType.RIAL,
				null,
				null);
		Deposit depositBlocked = new Deposit(
				0,
				"3875568200",
				123123,
				321321,
				DepositState.BLOCK,
				DepositType.LONG_TERM,
				CurrencyType.RIAL,
				null,
				null);
		depositTo = new Deposit(
				0,
				"3875568255",
				11111,
				100,
				DepositState.OPEN,
				DepositType.LONG_TERM,
				CurrencyType.RIAL,
				null,
				null);
		depositFrom = new Deposit(
				0,
				"3875568255",
				22222,
				100000,
				DepositState.OPEN,
				DepositType.LONG_TERM,
				CurrencyType.RIAL,
				null,
				null);
		CustomerDto customer = new CustomerDto(
				1,
				"3875568255",
				"Arash",
				"Yeg",
				"sdfsdf",
				"09142342354",
				null,
				null);

		List<Deposit> depositList = new ArrayList<>();
		depositList.add(new Deposit(1, "3865478925", 12321, 10, DepositState.BLOCK, DepositType.DEPOSIT_GHARZ,
				CurrencyType.DOLLAR, new Date(), new Date()));
		depositList.add(new Deposit(2, "3865478926", 12322, 20, DepositState.BLOCK, DepositType.DEPOSIT_GHARZ,
				CurrencyType.DOLLAR, new Date(), new Date()));
		depositList.add(new Deposit(3, "3865478926", 12323, 30, DepositState.BLOCK, DepositType.DEPOSIT_JARI,
				CurrencyType.DOLLAR, new Date(), new Date()));

		when(repo.save(ArgumentMatchers.<Deposit>any()))
				.thenAnswer(i -> i.getArguments()[0]);
		when(customerResource.findCustomerByNin("3875568255"))
				.thenReturn(Optional.of(customer));
		when(repo.findAll()).thenReturn(depositList);
		when(repo.findByDepositNumber(anyInt())).thenReturn(Optional.of(deposit));
		when(repo.findByDepositNumber(0)).thenReturn(Optional.empty());
		when(repo.findByDepositNumber(99999)).thenReturn(Optional.empty());
		when(repo.findByDepositNumber(123123)).thenReturn(Optional.of(depositBlocked));
		when(repo.findByDepositNumber(11111)).thenReturn(Optional.of(depositTo));
		when(repo.findByDepositNumber(22222)).thenReturn(Optional.of(depositFrom));

		when(repo.findByDepositType(ArgumentMatchers.<DepositType>any()))
				.thenAnswer(i -> filterByDepositType(depositList, (DepositType) i.getArguments()[0]));

		TransactionDto transaction = new TransactionDto(0, 101,
				TransactionStatus.successful, TransactionType.deposit, 123, 123, " ");
		when(transactionResource.registerTransaction(transaction)).thenReturn(Optional.of(transaction));

		// TransactionDto transaction3=new TransactionDto(0, 99,
		// TransactionStatus.successful, TransactionType.deposit, 123, 123, " ");
		// when(transactionResource.registerTransaction(transaction3)).thenReturn(Optional.of(transaction3));

		TransactionDto transaction2=new TransactionDto(0, 101,
		TransactionStatus.successful, TransactionType.deposit, 124, 124, " ");
		when(transactionResource.registerTransaction(transaction2)).thenReturn(Optional.empty());

		when(transactionResource.registerTransaction(ArgumentMatchers.<TransactionDto>any()))
				.thenAnswer(i -> Optional.of(i.getArguments()[0]));
	}

	private List<Deposit> filterByDepositType(List<Deposit> list, DepositType type) {
		return list.stream()
				.filter((item) -> item.getDepositType() == type)
				.collect(Collectors.toList());
	}

	@Test
	void checkCreateDeposit() {
		Deposit testCase = service.createDeposit(
				"3875568255",
				12345,
				DepositType.LONG_TERM,
				CurrencyType.RIAL);
		assertNotNull(testCase);
		assertEquals("3875568255", testCase.getNin());
		assertEquals(DepositType.LONG_TERM, testCase.getDepositType());
		assertEquals(CurrencyType.RIAL, testCase.getCurrencyType());
		assertEquals("3875568255", testCase.getNin());

	}

	@Test
	void checkCreateDepositException() {
		assertThrows(NinNotFoundException.class,
				() -> service.createDeposit("3875568000",12345,
						DepositType.DEPOSIT_JARI, CurrencyType.DOLLAR));
	}

	@Test
	void checkGetListOfDeposit() {
		assertEquals(3, service.getListDeposit().size());
	}

	@Test
	void checkGetListOfdepositCustomers() {

		assertEquals(2, service.getListOfDepositOfCustomers(DepositType.DEPOSIT_GHARZ).size());
		assertEquals(1, service.getListOfDepositOfCustomers(DepositType.DEPOSIT_JARI).size());
		assertEquals(0, service.getListOfDepositOfCustomers(DepositType.LONG_TERM).size());

		assertEquals(DepositType.DEPOSIT_JARI,
				service.getListOfDepositOfCustomers(DepositType.DEPOSIT_JARI).get(0).getDepositType());

	}

	@Test
	void checkChangeDepositState() {
		assertEquals(DepositState.BLOCK_DEPOSIT,
				service.changeDepositState(DepositState.BLOCK_DEPOSIT, 12120).getDepositState());
	}

	@Test
	void checkChangeDepositStateException() {
		assertThrows(DepositNotFoundException.class, () -> service.changeDepositState(DepositState.BLOCK_WITHDRAW, 0));
	}

	@Test
	void checkChangeDepositBlockException() {
		assertThrows(DepositIsBlockException.class,
				() -> service.changeDepositState(DepositState.BLOCK_WITHDRAW, 123123));
	}

	@Test
	void checkDepositing() {
		assertEquals(201, service.depositing(123, 101).getBalance());
		assertThrows(DepositNotFoundException.class, () -> service.depositing(0, 101));
	}

	@Test
	void checkDepositingException() {
		deposit.setDepositState(DepositState.BLOCK);
		assertThrows(DepositIsBlockException.class, () -> service.depositing(123, 1));
		deposit.setDepositState(DepositState.BLOCK_DEPOSIT);
		assertThrows(DepositIsBlockException.class, () -> service.depositing(123, 1));
		deposit.setDepositState(DepositState.BLOCK_WITHDRAW);
		deposit.setBalance(100);
		assertEquals(201, service.depositing(123, 101).getBalance());
		
	}

	@Test
	void checkDepositingTransactionException() {
		assertThrows(TransactionNotCommitted.class, () -> service.depositing(124, 101));
	}
	@Test
	void checkWithdraw() {
		assertEquals(1, service.withdraw(123, 99).getBalance());
		assertThrows(DepositNotFoundException.class, () -> service.withdraw(0, 101));
	}

	@Test
	void checkWithdrawException() {
		assertThrows(NotEnoughBalance.class, () -> service.withdraw(123, 101));
		deposit.setDepositState(DepositState.BLOCK);
		assertThrows(DepositIsBlockException.class, () -> service.withdraw(123, 1));
		deposit.setDepositState(DepositState.BLOCK_WITHDRAW);
		assertThrows(DepositIsBlockException.class, () -> service.withdraw(123, 1));
		deposit.setDepositState(DepositState.BLOCK_DEPOSIT);
		deposit.setBalance(100);
		assertEquals(1, service.withdraw(123, 99).getBalance());
	}
	
	@Test
	void checkTransfer() {
		// assertEquals(2,service.transfer(22222, 11111, 10000).size());
		assertEquals(10100, service.transfer(22222, 11111, 10000).get(0).getBalance());
		depositFrom.setDepositState(DepositState.BLOCK_DEPOSIT);
		assertEquals(20100, service.transfer(22222, 11111, 10000).get(0).getBalance());
		depositTo.setDepositState(DepositState.BLOCK_WITHDRAW);
		assertEquals(30100, service.transfer(22222, 11111, 10000).get(0).getBalance());

	}

	@Test
	void checkTransferBlockException() {
		depositFrom.setDepositState(DepositState.BLOCK);
		assertThrows(DepositIsBlockException.class, () -> service.transfer(22222, 11111, 100));
		depositFrom.setDepositState(DepositState.BLOCK_WITHDRAW);
		assertThrows(DepositIsBlockException.class, () -> service.transfer(22222, 11111, 100));
		depositFrom.setDepositState(DepositState.OPEN);
		depositTo.setDepositState(DepositState.BLOCK);
		assertThrows(DepositIsBlockException.class, () -> service.transfer(22222, 11111, 100));
		depositTo.setDepositState(DepositState.BLOCK_DEPOSIT);
		assertThrows(DepositIsBlockException.class, () -> service.transfer(22222, 11111, 100));
	}

	@Test
	void checkTransferWithdrawException() {
		assertThrows(NotEnoughBalance.class, () -> service.transfer(22222, 11111, 1001000));
	}

	@Test
	void checkTransferNotFoundException() {
		assertThrows(DepositNotFoundException.class, () -> service.transfer(99999, 11111, 20));
		assertThrows(DepositNotFoundException.class, () -> service.transfer(11111, 99999, 20));
		assertThrows(DepositNotFoundException.class, () -> service.transfer(99999, 99999, 20));
	}

	@Test
	void checkDisplayBalance() {
		assertEquals(321321, service.displayBalance(123123));
		assertThrows(DepositNotFoundException.class, () -> service.displayBalance(0));
	}

}
