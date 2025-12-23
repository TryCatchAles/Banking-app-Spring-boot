package com.bank.app.controller;

import com.bank.app.entity.Account;
import com.bank.app.entity.Transaction;
import com.bank.app.service.BankingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BankingController {

    private final BankingService bankingService;

    public BankingController(BankingService bankingService) {
        this.bankingService = bankingService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Map<String, String> request) {
        return ResponseEntity.ok(bankingService.createAccount(request.get("email"), request.get("password")));
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Map<String, String> request) {
        return ResponseEntity.ok(bankingService.login(request.get("email"), request.get("password")));
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody Map<String, Object> request) {
        Long senderId = Long.valueOf(request.get("senderId").toString());
        String receiverEmail = (String) request.get("receiverEmail");
        BigDecimal amount = new BigDecimal(request.get("amount").toString());
        
        bankingService.transfer(senderId, receiverEmail, amount);
        return ResponseEntity.ok("Transfer successful");
    }

    @GetMapping("/history/{accountId}")
    public ResponseEntity<List<Transaction>> getHistory(@PathVariable Long accountId) {
        return ResponseEntity.ok(bankingService.getHistory(accountId));
    }
    
    @GetMapping("/account/{accountId}")
    public ResponseEntity<Account> getAccount(@PathVariable Long accountId) {
        return ResponseEntity.ok(bankingService.getAccount(accountId));
    }
}
