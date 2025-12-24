package com.bank.app.service;

import com.bank.app.entity.Account;
import com.bank.app.entity.Transaction;
import com.bank.app.repository.AccountRepository;
import com.bank.app.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BankingService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public BankingService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public Account createAccount(String email, String password) {
        if (accountRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Error: Email already exists");
        }
        return accountRepository.save(new Account(email, password));
    }

    public Account login(String email, String password) {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Error: Account with this email does not exist"));
        if (!account.getPassword().equals(password)) {
            throw new RuntimeException("Error: Invalid password");
        }
        return account;
    }

    @Transactional
    public void transfer(Long senderId, String receiverEmail, BigDecimal amount) {
        Account sender = accountRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Error: Sender account not found"));
        Account receiver = accountRepository.findByEmail(receiverEmail)
                .orElseThrow(() -> new RuntimeException("Error: Receiver email does not exist"));
        if (sender.getId().equals(receiver.getId())){
            throw new RuntimeException("Error: Sender and receiver cannot be the same");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new RuntimeException("Error: Invalid amount");
        }
        if (sender.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Error: Insufficient funds for this transfer");
        }

        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));

        accountRepository.save(sender);
        accountRepository.save(receiver);

        transactionRepository.save(new Transaction(sender, receiver, amount));
    }

    public List<Transaction> getHistory(Long accountId) {
        return transactionRepository.findBySenderIdOrReceiverIdOrderByTimestampDesc(accountId, accountId);
    }
    
    public Account getAccount(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Error: Account not found"));
    }
}
