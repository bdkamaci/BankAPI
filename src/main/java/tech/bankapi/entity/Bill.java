package tech.bankapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "bills")
@Getter
@Setter
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_id")
    private Long id;

    @Column(name = "bill_type", nullable = false)
    private String type;

    @Column(name = "bill_amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "bill_dueDate", nullable = false)
    private LocalDate dueDate;

    @Column(name = "bill_isPaid", nullable = false)
    private Boolean isPaid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bill_account_id", referencedColumnName = "account_id", nullable = false)
    private Account account;
}
