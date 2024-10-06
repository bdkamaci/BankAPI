package tech.bankapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.bankapi.model.Transaction;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByFromAccountId(Long id);
    List<Transaction> findByFromAccountIdOrToAccountId(Long fromAccountId, Long toAccountId);
}
