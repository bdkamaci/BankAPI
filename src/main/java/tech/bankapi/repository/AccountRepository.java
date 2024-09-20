package tech.bankapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.bankapi.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
