package io.pismo.service.impl;

import io.pismo.dao.TransactionDao;
import io.pismo.exceptions.TransactionNotFoundException;
import io.pismo.repo.OperationTypeRepository;
import io.pismo.repo.TransactionRepository;
import io.pismo.service.AccountService;
import io.pismo.service.OperationTypeService;
import io.pismo.service.TransactionService;
import io.pismo.service.dto.AccountDto;
import io.pismo.service.dto.CreateTransactionRequestDto;
import io.pismo.service.dto.OperationTypeDto;
import io.pismo.service.dto.TransactionDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private OperationTypeService operationTypeService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    @Transactional
    public long createTransaction(CreateTransactionRequestDto requestDto) {
        AccountDto accountDto = accountService.getAccount(requestDto.getAccountId());
        OperationTypeDto operationTypeDto = operationTypeService.getOperationTypeDetails(requestDto.getOperationTypeId());
        boolean isCreditTransaction = operationTypeDto.isCreditTransaction();
        double balanceAmount = requestDto.getAmount();
        if(isCreditTransaction){
            List<TransactionDao> transactionDaoList = transactionRepository.getAllBalanceTransactions(requestDto.getAccountId());
            balanceAmount = this.processSettlements(transactionDaoList,requestDto.getAmount());
            transactionRepository.saveAll(transactionDaoList);
        }
        TransactionDao transactionDao = TransactionDao.builder()
                .accountId(accountDto.getAccountId())
                .operationTypeId(operationTypeDto.getOperationTypeId())
                .amount(requestDto.getAmount() * operationTypeDto.getOperationValue())
                .balanceAmount(balanceAmount * operationTypeDto.getOperationValue())
                .build();
        transactionDao =transactionRepository.save(transactionDao);
        return transactionDao.getTransactionId();
    }

    @Override
    public TransactionDto getTransaction(long transactionId) {
        return transactionRepository.findByTransactionId(transactionId).map(dao ->{
            TransactionDto dto  =new TransactionDto();
            BeanUtils.copyProperties(dao,dto);
            return dto;
        }).orElseThrow(TransactionNotFoundException::new);
    }

    public double  processSettlements(List<TransactionDao> transactionDaoList ,double creditAmount){
        if(CollectionUtils.isEmpty(transactionDaoList)){
            return creditAmount;
        }
        for(TransactionDao transaction : transactionDaoList){
            if(Math.abs(transaction.getBalanceAmount()) <= creditAmount){
                double balanceAmount = transaction.getBalanceAmount();
                transaction.setBalanceAmount(0);
                creditAmount-=Math.abs(balanceAmount);
            }else{
                transaction.setBalanceAmount(transaction.getAmount() + creditAmount);
                creditAmount = 0;
                break;
            }
        }
        return creditAmount;
    }
}
