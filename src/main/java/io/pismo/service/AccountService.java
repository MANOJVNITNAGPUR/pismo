package io.pismo.service;

import io.pismo.service.dto.AccountDto;
import io.pismo.service.dto.RegisterAccountRequestDto;
import io.pismo.service.dto.RegisterAccountResponseDto;
import io.pismo.service.dto.ResponseDto;

public interface AccountService {

    RegisterAccountResponseDto registerAccount(RegisterAccountRequestDto registerAccountDto);

    AccountDto getAccount(long accountId);

}
