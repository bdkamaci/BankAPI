package tech.bankapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.bankapi.entity.SecurityLog;

public interface SecurityLogRepository extends JpaRepository<SecurityLog, Long> {
}
