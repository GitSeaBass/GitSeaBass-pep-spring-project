package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.ConflictingUsernameException;
import com.example.exception.InvalidBodyException;
import com.example.exception.UnauthorizedLoginException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account postAccount(Account account) throws InvalidBodyException, ConflictingUsernameException {
        // fails if username blank, password at least 4 long, and no account with that name
        if (account.getUsername().length() == 0 || account.getPassword().length() < 4) {
            throw new InvalidBodyException("Account to be registered invalid");
        } else if (accountRepository.findByUsername(account.getUsername()) != null) {
            // if finds an existing user, then conflict
            throw new ConflictingUsernameException("Username already exists");
        }
        return accountRepository.save(account);
    }

    public Account login(Account account) throws UnauthorizedLoginException {
        Account foundAccount = accountRepository.findByUsername(account.getUsername());
        if (foundAccount != null && account.getPassword().equals(foundAccount.getPassword())) {
            return foundAccount;
        }
        throw new UnauthorizedLoginException("Login attempt failed");
    }
}
