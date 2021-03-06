package io.pismo.repo;

import io.pismo.dao.TransactionDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends PagingAndSortingRepository<TransactionDao,Long> {
    Optional<TransactionDao> findByTransactionId(Long transactionId);

    @Query("select txn from transactions txn where txn.balanceAmount < 0.0 and txn.accountId = :accountId")
    List<TransactionDao> getAllBalanceTransactions(@Param("accountId") long accountId);

}
