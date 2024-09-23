package tech.bankapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionResponse {
    private Long id;
    private BigDecimal amount;
    private LocalDateTime date;
    private String description;
    private Long fromAccountId;
    private Long toAccountId;
}
