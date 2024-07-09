package com.id3.app.service.impl;

import com.id3.app.dto.AccountDto;
import com.id3.app.exception.AccountNotFoundException;
import com.id3.app.model.Account;
import com.id3.app.repository.AccountRepository;
import com.id3.app.request.CreateAccountRequest;
import com.id3.app.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.id3.app.mapper.AccountMapper.ACCOUNT_MAPPER;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public AccountDto createAccount(CreateAccountRequest createAccountRequest) {
        log.info("Creating new account for user ID: {}", createAccountRequest.getUserId());
        Account account =ACCOUNT_MAPPER.createAccount(createAccountRequest);
        Account savedAccount= accountRepository.save(account);
        log.info("Account created successfully with ID: {}", savedAccount.getId());
        return ACCOUNT_MAPPER.toAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAccountsByUserId(Long userId) {
        log.info("Fetching accounts for user ID: {}", userId);
        return ACCOUNT_MAPPER.toAccountDtoList(accountRepository.findByUserId(userId).stream().collect(Collectors.toList()));
    }

    @Override
    public void deleteAccount(Long id) {
        log.info("Deleting account with ID: {}", id);
        accountRepository.deleteById(id);
        log.info("Account with ID: {} deleted successfully", id);
    }

    @Override
    public void transferBalance(Long fromAccountId, Long toAccountId, Double amount) {
        log.info("Transferring balance from account ID: {} to account ID: {}", fromAccountId, toAccountId);
        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new AccountNotFoundException("From Account not found"));
        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new AccountNotFoundException("To Account not found"));

        if (!fromAccount.getCurrency().equals(toAccount.getCurrency())) {
            log.error("Currency mismatch: {} vs {}", fromAccount.getCurrency(), toAccount.getCurrency());
            throw new RuntimeException("Currency mismatch");
        }

        if (fromAccount.getBalance() < amount) {
            log.error("Insufficient balance in account ID: {}", fromAccountId);
            throw new RuntimeException("Insufficient balance");
        }

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        log.info("Balance transferred successfully from account ID: {} to account ID: {}", fromAccountId, toAccountId);
    }


}
