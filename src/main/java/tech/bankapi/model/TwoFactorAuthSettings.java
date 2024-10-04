package tech.bankapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "two_factor_auth_settings")
public class TwoFactorAuthSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "two_factor_auth_id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "method")
    private String method; // E.g., SMS, EMAIL

    @Column(name = "enabled")
    private boolean enabled;
}
