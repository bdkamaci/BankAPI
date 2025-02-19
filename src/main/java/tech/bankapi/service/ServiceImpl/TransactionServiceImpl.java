package tech.bankapi.service.ServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.bankapi.core.config.ModelMapper.ModelMapperService;
import tech.bankapi.dto.request.TransactionRequest;
import tech.bankapi.dto.response.TransactionResponse;
import tech.bankapi.model.Account;
import tech.bankapi.model.Transaction;
import tech.bankapi.exception.GlobalExceptionHandler;
import tech.bankapi.repository.AccountRepository;
import tech.bankapi.repository.TransactionRepository;
import tech.bankapi.service.TransactionService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final ModelMapperService modelMapperService;

    @Override
    public List<TransactionResponse> getAccountTransactions(Long accountId) {
        List<Transaction> transactions = transactionRepository.findByFromAccountId(accountId);
        return transactions.stream()
                .map(transaction -> modelMapperService.forResponse().map(transaction, TransactionResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionResponse> getAllTransactions() {
        return transactionRepository.findAll().stream()
                .map(transaction -> modelMapperService.forResponse().map(transaction, TransactionResponse.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public TransactionResponse transferMoney(Long id, TransactionRequest transactionRequest) {
        Account fromAccount = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sender account not found"));
        Account toAccount = accountRepository.findById(transactionRequest.getToAccountId())
                .orElseThrow(() -> new RuntimeException("Receiver account not found"));

        BigDecimal amount = transactionRequest.getAmount();

        // Check for sufficient funds in the sender's account
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new GlobalExceptionHandler.InsufficientFundsException("Insufficient funds in sender's account");
        }

        // Deduct from sender and add to receiver
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        // Save updated accounts
        accountRepository.saveAndFlush(fromAccount);
        accountRepository.saveAndFlush(toAccount);

        // Create transaction log
        Transaction transaction = modelMapperService.forRequest().map(transactionRequest, Transaction.class);
        transactionRepository.save(transaction);

        return modelMapperService.forResponse().map(transaction, TransactionResponse.class);
    }
}
