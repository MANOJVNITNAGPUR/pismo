package io.pismo.repo;

import io.pismo.dao.TransactionDao;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends PagingAndSortingRepository<TransactionDao,Long> {
    Optional<TransactionDao> findByTransactionId(Long transactionId);
}
