package tech.bankapi.service;

import tech.bankapi.dto.request.AccountRequest;
import tech.bankapi.dto.response.AccountResponse;
import tech.bankapi.dto.response.TransactionResponse;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    AccountResponse createAccount(AccountRequest accountRequest);
    AccountResponse updateAccount(AccountRequest accountRequest);
    void deleteAccount(Long id);
    List<AccountResponse> getUserAccounts(Long userId);
    List<AccountResponse> getAllAccounts();

    BigDecimal getBalance(Long accountId);
    List<TransactionResponse> getAccountTransactions(Long accountId);
}
