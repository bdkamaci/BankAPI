package tech.bankapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.bankapi.model.TwoFactorAuthSettings;

public interface TwoFactorAuthSettingsRepository extends JpaRepository<TwoFactorAuthSettings, Long> {
    TwoFactorAuthSettings findByUserId(Long userId);
}
