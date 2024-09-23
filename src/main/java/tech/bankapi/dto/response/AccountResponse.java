package tech.bankapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountResponse {
    private Long id;
    private String number;
    private BigDecimal balance;
    private Long userId;

    private List<BillResponse> bills;
    private List<TransactionResponse> outgoingTransactions;
    private List<TransactionResponse> incomingTransactions;
}
