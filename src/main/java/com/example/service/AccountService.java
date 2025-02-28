package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.InvalidBodyException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account postAccount(Account account) /*throws InvalidBodyException*/ {
        // fails if username blank, password at least 4 long, and no account with that name
        /*if (account.getUsername().length() == 0 || account.getPassword().length() < 4) {
            throw new InvalidBodyException("Account to be registered invalid");
        }*/
        return accountRepository.save(account);
    }
}
