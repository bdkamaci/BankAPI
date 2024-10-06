package tech.bankapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.bankapi.model.Account;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByUserId(Long id);
}
