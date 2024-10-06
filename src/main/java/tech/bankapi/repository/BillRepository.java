package tech.bankapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.bankapi.model.Bill;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {
    List<Bill> findByAccountId(Long id);
}
