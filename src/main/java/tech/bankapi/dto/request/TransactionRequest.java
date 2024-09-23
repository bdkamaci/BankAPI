package tech.bankapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionRequest {
    @NonNull
    private BigDecimal amount;

    @NonNull
    private String description;

    @NonNull
    private Long fromAccountId;

    @NonNull
    private Long toAccountId;
}
