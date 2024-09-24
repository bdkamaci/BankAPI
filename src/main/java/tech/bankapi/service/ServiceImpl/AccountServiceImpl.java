package tech.bankapi.service.ServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.bankapi.core.config.ModelMapper.ModelMapperService;
import tech.bankapi.dto.request.AccountRequest;
import tech.bankapi.dto.request.TransactionRequest;
import tech.bankapi.dto.response.AccountResponse;
import tech.bankapi.entity.Account;
import tech.bankapi.entity.Transaction;
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

    @Transactional
    @Override
    public AccountResponse transferMoney(TransactionRequest transactionRequest) {
        Account fromAccount = accountRepository.findById(transactionRequest.getFromAccountId())
                .orElseThrow(() -> new RuntimeException("Source account not found"));
        Account toAccount = accountRepository.findById(transactionRequest.getToAccountId())
                .orElseThrow(() -> new RuntimeException("Destination account not found"));

        // Money Transfer Logic
        if (fromAccount.getBalance().compareTo(transactionRequest.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(transactionRequest.getAmount()));
        toAccount.setBalance(toAccount.getBalance().add(transactionRequest.getAmount()));

        // Saving Updated Accounts
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        // Creating a Transaction Record
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        transactionRepository.save(transaction);

        return modelMapperService.forResponse().map(fromAccount, AccountResponse.class);
    }
}
