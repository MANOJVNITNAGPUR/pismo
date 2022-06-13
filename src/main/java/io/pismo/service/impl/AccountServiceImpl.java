package io.pismo.service.impl;

import io.pismo.constants.AccountsConstants;
import io.pismo.dao.AccountDao;
import io.pismo.exceptions.AccountAlreadyRegisterException;
import io.pismo.exceptions.AccountNotFoundException;
import io.pismo.repo.AccountRepository;
import io.pismo.service.AccountService;
import io.pismo.service.dto.AccountDto;
import io.pismo.service.dto.RegisterAccountRequestDto;
import io.pismo.service.dto.RegisterAccountResponseDto;
import io.pismo.service.dto.ResponseDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    @Transactional
    public RegisterAccountResponseDto registerAccount(RegisterAccountRequestDto registerAccountDto) {
        if(isAccountRegistrationExists(registerAccountDto.getDocumentNumber())){
            throw new AccountAlreadyRegisterException();
        }
        AccountDao accountDao = AccountDao.builder().documentNumber(registerAccountDto.getDocumentNumber()).build();
        accountDao = accountRepository.saveAndFlush(accountDao);
         return RegisterAccountResponseDto.builder().accountId(accountDao.getAccountId()).build();
    }

    @Override
    @Transactional(readOnly = true)
    public AccountDto getAccount(long accountId) {
        Optional<AccountDao> accountDao = accountRepository.findByAccountId(accountId);
        return accountDao.map(dao ->{
            AccountDto accountDto = new AccountDto();
             BeanUtils.copyProperties(dao,accountDto);
            return accountDto;
        }).orElseThrow(AccountNotFoundException::new);
    }

    private boolean isAccountRegistrationExists(String documentNumber) {
        return accountRepository.findByDocumentNumber(documentNumber).isPresent();
    }


}
