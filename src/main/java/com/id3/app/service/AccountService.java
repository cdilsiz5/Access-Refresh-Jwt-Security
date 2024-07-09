package com.id3.app.service;


import com.id3.app.dto.AccountDto;
import com.id3.app.request.CreateAccountRequest;

import java.util.List;

public interface AccountService {
    AccountDto createAccount(CreateAccountRequest createAccountRequest);
    List<AccountDto> getAccountsByUserId(Long userId);
    void deleteAccount(Long id);
    void transferBalance(Long fromAccountId, Long toAccountId, Double amount);
}
