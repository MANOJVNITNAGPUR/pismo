package io.pismo.service;

import io.pismo.dao.AccountDao;
import io.pismo.dao.TransactionDao;
import io.pismo.exceptions.AccountNotFoundException;
import io.pismo.exceptions.OperationTypeNotFoundException;
import io.pismo.exceptions.TransactionNotFoundException;
import io.pismo.repo.TransactionRepository;
import io.pismo.service.dto.AccountDto;
import io.pismo.service.dto.CreateTransactionRequestDto;
import io.pismo.service.dto.OperationTypeDto;
import io.pismo.service.dto.TransactionDto;
import io.pismo.service.impl.AccountServiceImpl;
import io.pismo.service.impl.TransactionServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private AccountServiceImpl accountService;

    @Mock
    private OperationTypeService operationTypeService;

    @Mock
     private TransactionRepository transactionRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test(expected = AccountNotFoundException.class)
    public void createTransaction_AccountNotFoundExceptionTest(){
        Mockito.when(accountService.getAccount(Mockito.eq(1L))).thenThrow(new AccountNotFoundException());
        transactionService.createTransaction(this.getRequestDto());
    }


    @Test(expected = OperationTypeNotFoundException.class)
    public void createTransaction_InvalidOperationTypeExceptionTest(){
        Mockito.when(accountService.getAccount(Mockito.eq(1L))).thenReturn(this.getAccountDto());
        Mockito.when(operationTypeService.getOperationTypeDetails(Mockito.eq(1))).thenThrow(new OperationTypeNotFoundException());
        transactionService.createTransaction(this.getRequestDto());
    }

    @Test
    public void createTransactionTest(){
        Mockito.when(accountService.getAccount(Mockito.eq(1L))).thenReturn(this.getAccountDto());
        Mockito.when(operationTypeService.getOperationTypeDetails(Mockito.eq(1))).thenReturn(OperationTypeDto.builder().operationTypeId(1).operationValue(1).build());
        TransactionDao transactionDao = TransactionDao.builder()
                .accountId(1l)
                .operationTypeId(1)
                .amount(200).transactionId(1l)
                .build();
        Mockito.when(transactionRepository.save(Mockito.any(TransactionDao.class))).thenReturn(transactionDao);
       long transactionId =  transactionService.createTransaction(this.getRequestDto());
       Assert.assertEquals(1l,transactionId);
    }



    @Test
    public void getTransactionTest(){
        TransactionDao transactionDao = TransactionDao.builder().transactionId(1L).accountId(1l).operationTypeId(1).amount(100).createdOn(LocalDateTime.now()).build();
        Mockito.when(transactionRepository.findByTransactionId(Mockito.eq(1L))).thenReturn(Optional.of(transactionDao));
        TransactionDto transactionDto = transactionService.getTransaction(1l);
        Assert.assertEquals(1l,transactionDto.getTransactionId());
    }

    @Test(expected = TransactionNotFoundException.class)
    public void transactionNotFoundTest(){
        Mockito.when(transactionRepository.findByTransactionId(Mockito.eq(1L))).thenReturn(Optional.empty());
        transactionService.getTransaction(1l);
    }

    @Test
    public void processSettlementsTest(){
        List<TransactionDao> transactionDaoList = new ArrayList<>();
        transactionDaoList.add(getTransactionDao(1,-50.0));
        transactionDaoList.add(getTransactionDao(2,-23.5));
        transactionDaoList.add(getTransactionDao(3,-18.7));
       double remainingCreditAmount =  transactionService.processSettlements(transactionDaoList,60);
        transactionDaoList.forEach(transactionDao -> {
            System.out.println(transactionDao.getAmount() + " _ " +transactionDao.getBalanceAmount());
        });
        System.out.println(remainingCreditAmount);

        System.out.println("-----------------------------");
         remainingCreditAmount =  transactionService.processSettlements(transactionDaoList,100);
        transactionDaoList.forEach(transactionDao -> {
            System.out.println(transactionDao.getAmount() + " _ " +transactionDao.getBalanceAmount());
        });
        System.out.println(remainingCreditAmount);


    }

    private TransactionDao getTransactionDao(long id,double amount){
        return TransactionDao.builder().balanceAmount(amount).amount(amount).transactionId(id).build();
    }


    private CreateTransactionRequestDto getRequestDto(){
        return  CreateTransactionRequestDto.builder().accountId(1l).operationTypeId(1).amount(200.0).build();
    }

    private AccountDto getAccountDto(){
        AccountDto accountDto =  new AccountDto();
        accountDto.setAccountId(1l);
        accountDto.setDocumentNumber("12345");
        return  accountDto;
    }
}
