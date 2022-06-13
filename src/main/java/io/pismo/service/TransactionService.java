package io.pismo.service;

import io.pismo.service.dto.CreateTransactionRequestDto;
import io.pismo.service.dto.TransactionDto;

public interface TransactionService {

    long createTransaction(CreateTransactionRequestDto requestDto);
    TransactionDto getTransaction(long transactionId);

}
