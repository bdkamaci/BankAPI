package tech.bankapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BillRequest {
    @NonNull
    private String type;

    @NonNull
    private BigDecimal amount;

    @NonNull
    private LocalDate dueDate;

    @NonNull
    private Long accountId;
}
