package tech.bankapi.service.ServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.bankapi.core.config.ModelMapper.ModelMapperService;
import tech.bankapi.dto.request.AccountRequest;
import tech.bankapi.dto.response.AccountResponse;
import tech.bankapi.dto.response.TransactionResponse;
import tech.bankapi.model.Account;
import tech.bankapi.model.Transaction;
import tech.bankapi.exception.GlobalExceptionHandler;
import tech.bankapi.repository.AccountRepository;
import tech.bankapi.repository.TransactionRepository;
import tech.bankapi.service.AccountService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final ModelMapperService modelMapperService;
    private final TransactionRepository transactionRepository;

    @Override
    public AccountResponse createAccount(AccountRequest accountRequest) {
        if(accountRepository.findById(modelMapperService.forRequest().map(accountRequest, Account.class).getId()).isEmpty()) {
            Account account = modelMapperService.forRequest().map(accountRequest, Account.class);
            account = accountRepository.save(account);
            return modelMapperService.forResponse().map(account, AccountResponse.class);
        } else {
            throw new RuntimeException("Account already exists");
        }
    }

    @Override
    public AccountResponse updateAccount(AccountRequest accountRequest) {
        Account account = accountRepository.findById(modelMapperService.forRequest().map(accountRequest, Account.class).getId())
                .orElseThrow(() -> new RuntimeException("Account not found!"));
        modelMapperService.forRequest().map(accountRequest, account);
        accountRepository.saveAndFlush(account);
        return modelMapperService.forResponse().map(account, AccountResponse.class);
    }


    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));
        accountRepository.delete(account);
    }

    @Override
    public List<AccountResponse> getUserAccounts(Long userId) {
        List<Account> accounts = accountRepository.findByUserId(userId);
        return accounts.stream()
                .map(account -> modelMapperService.forResponse().map(account, AccountResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountResponse> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(account -> modelMapperService.forResponse().map(account, AccountResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal getBalance(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + accountId));
        return account.getBalance();
    }

    @Override
    public List<TransactionResponse> getAccountTransactions(Long accountId) {
        // Fetch the account by ID to check if it exists
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Account not found with id: " + accountId));

        // Fetch the transactions where the account is either the fromAccount or toAccount
        List<Transaction> transactions = transactionRepository.findByFromAccountIdOrToAccountId(accountId, accountId);

        // Map each transaction to a TransactionResponse
        return transactions.stream()
                .map(transaction -> {
                    TransactionResponse response = new TransactionResponse();
                    response.setId(transaction.getId());
                    response.setAmount(transaction.getAmount());
                    response.setDate(transaction.getDate());
                    response.setDescription(transaction.getDescription());
                    response.setFromAccountId(transaction.getFromAccount().getId());
                    response.setToAccountId(transaction.getToAccount().getId());
                    return response;
                })
                .collect(Collectors.toList());
    }
}
