package tech.bankapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.bankapi.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
