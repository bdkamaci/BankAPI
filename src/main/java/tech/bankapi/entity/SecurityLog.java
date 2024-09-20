package tech.bankapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "logs")
@Getter
@Setter
public class SecurityLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long id;

    @Column(name = "log_date", nullable = false)
    private LocalDateTime date;

    @Column(name = "log_action", nullable = false)
    private String action;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "log_user_id", referencedColumnName = "user_id", nullable = false)
    private User user;
}
