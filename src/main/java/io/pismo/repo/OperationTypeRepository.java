package io.pismo.repo;

import io.pismo.dao.OperationTypeDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OperationTypeRepository extends JpaRepository<OperationTypeDao,Integer> {

    Optional<OperationTypeDao> findByOperationTypeId(int operationTypeId);
}
