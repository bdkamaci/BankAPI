package tech.bankapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.bankapi.model.SecurityLog;

import java.util.List;

public interface SecurityLogRepository extends JpaRepository<SecurityLog, Long> {

    List<SecurityLog> findByUserId(Long id);
}
