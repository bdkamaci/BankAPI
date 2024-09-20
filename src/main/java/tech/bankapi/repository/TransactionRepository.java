package tech.bankapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.bankapi.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
