package tech.bankapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountRequest {
    @NonNull
    private String number;

    @NonNull
    private BigDecimal balance;

    @NonNull
    private Long userId;
}
