package tech.bankapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BillResponse {
    private Long id;
    private String type;
    private BigDecimal amount;
    private LocalDate dueDate;
    private Boolean isPaid;
    private Long accountId;
}
