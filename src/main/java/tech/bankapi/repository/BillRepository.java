package tech.bankapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.bankapi.entity.Bill;

public interface BillRepository extends JpaRepository<Bill, Long> {
}
