package tech.bankapi.service;

import tech.bankapi.dto.request.TransactionRequest;
import tech.bankapi.dto.response.TransactionResponse;

import java.util.List;

public interface TransactionService {
    TransactionResponse createTransaction(TransactionRequest transactionRequest);
    TransactionResponse updateTransaction(TransactionRequest transactionRequest);
    void deleteTransaction(Long id);
    List<TransactionResponse> getAccountTransactions(Long accountId);
    List<TransactionResponse> getAllTransactions();

    TransactionResponse transferMoney(TransactionRequest transactionRequest);
}
