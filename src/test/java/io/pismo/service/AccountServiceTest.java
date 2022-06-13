package io.pismo.service;

import io.pismo.dao.AccountDao;
import io.pismo.exceptions.AccountAlreadyRegisterException;
import io.pismo.exceptions.AccountNotFoundException;
import io.pismo.repo.AccountRepository;
import io.pismo.service.dto.AccountDto;
import io.pismo.service.dto.RegisterAccountRequestDto;
import io.pismo.service.dto.RegisterAccountResponseDto;
import io.pismo.service.impl.AccountServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void registerAccountTest(){
        Mockito.when(accountRepository.findByDocumentNumber(Mockito.eq("12345"))).thenReturn(Optional.empty());
        Mockito.when(accountRepository.saveAndFlush(Mockito.any(AccountDao.class))).thenReturn(AccountDao.builder().documentNumber("12345").accountId(1).build());
        RegisterAccountRequestDto registerAccountDto = RegisterAccountRequestDto.builder().documentNumber("12345").build();
        RegisterAccountResponseDto responseDto = accountService.registerAccount(registerAccountDto);
        Assert.assertEquals(1, responseDto.getAccountId());
    }

    @Test(expected = AccountAlreadyRegisterException.class)
    public void alreadyRegisterAccountTest(){
        AccountDao accountDao = AccountDao.builder().documentNumber("12345").accountId(1).build();
        Mockito.when(accountRepository.findByDocumentNumber(Mockito.eq("12345"))).thenReturn(Optional.of(accountDao));
        RegisterAccountRequestDto registerAccountDto = RegisterAccountRequestDto.builder().documentNumber("12345").build();
        RegisterAccountResponseDto responseDto = accountService.registerAccount(registerAccountDto);
    }

    @Test(expected = AccountNotFoundException.class)
    public void accountNotExistsTest(){
        Mockito.when(accountRepository.findByAccountId(Mockito.eq(1L))).thenReturn(Optional.empty());
        accountService.getAccount(1);
    }

    @Test
    public void accountExistsTest(){
        AccountDao accountDao = AccountDao.builder().documentNumber("12345").accountId(1).build();
        Mockito.when(accountRepository.findByAccountId(Mockito.eq(1L))).thenReturn(Optional.of(accountDao));
       AccountDto accountDto =  accountService.getAccount(1);
        Assert.assertEquals(1, accountDto.getAccountId());
    }
}
