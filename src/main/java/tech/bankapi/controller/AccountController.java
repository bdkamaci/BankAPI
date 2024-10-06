package tech.bankapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tech.bankapi.dto.request.TransactionRequest;
import tech.bankapi.dto.response.AccountResponse;
import tech.bankapi.dto.response.TransactionResponse;
import tech.bankapi.service.AccountService;
import tech.bankapi.service.TransactionService;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getUserAccounts(@RequestParam Long userId) {
        List<AccountResponse> accounts = accountService.getUserAccounts(userId);
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<BigDecimal> getAccountBalance(@PathVariable Long id) {
        BigDecimal balance = accountService.getBalance(id);
        return ResponseEntity.ok(balance);
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<TransactionResponse>> getAccountTransactions(@PathVariable Long id) {
        List<TransactionResponse> transactions = accountService.getAccountTransactions(id);
        return ResponseEntity.ok(transactions);
    }

    @PostMapping("/{id}/transfer")
    public ResponseEntity<TransactionResponse> transferMoney(@PathVariable Long id, @RequestBody TransactionRequest transactionRequest) {
        TransactionResponse transactionResponse = transactionService.transferMoney(id, transactionRequest);
        return ResponseEntity.ok(transactionResponse);
    }
}
