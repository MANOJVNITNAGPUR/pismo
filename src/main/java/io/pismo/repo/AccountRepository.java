package io.pismo.repo;

import io.pismo.dao.AccountDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountDao,Long> {
    Optional<AccountDao> findByDocumentNumber(String documentNumber);

    Optional<AccountDao> findByAccountId(long accountId);
}
