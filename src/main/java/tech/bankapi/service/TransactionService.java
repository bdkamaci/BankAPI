package tech.bankapi.service;

import tech.bankapi.dto.request.TransactionRequest;
import tech.bankapi.dto.response.TransactionResponse;

import java.util.List;

public interface TransactionService {
    List<TransactionResponse> getAccountTransactions(Long accountId);
    List<TransactionResponse> getAllTransactions();
    TransactionResponse transferMoney(Long id, TransactionRequest transactionRequest);
}
