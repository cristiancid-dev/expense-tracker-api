package com.cristiancid.expensetracker.service;

import com.cristiancid.expensetracker.dto.CreateAccountRequest;
import com.cristiancid.expensetracker.dto.UpdateAccountRequest;
import com.cristiancid.expensetracker.exception.AccountAlreadyExistsException;
import com.cristiancid.expensetracker.exception.AccountNotFoundException;
import com.cristiancid.expensetracker.model.Account;
import com.cristiancid.expensetracker.model.User;
import com.cristiancid.expensetracker.repository.AccountRepository;
import com.cristiancid.expensetracker.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public Account createAccount(CreateAccountRequest request) {
        User user = getAuthenticatedUser();
        if (accountRepository.existsByUserAndName(user,request.getName())) {
            throw new AccountAlreadyExistsException("Account already exists");
        }
        Account newAccount = new Account(request.getName(), user);
        return accountRepository.save(newAccount);
    }

    public Page<Account> getAccounts(Pageable pageable) {
        User user = getAuthenticatedUser();
        return accountRepository.findByUser(user, pageable);
    }

    public Account getAccountById(Long id) {
        User user = getAuthenticatedUser();
        return accountRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
    }

    public Account updateAccount(Long id, UpdateAccountRequest request) {
        User user = getAuthenticatedUser();
        Account account = accountRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        if (accountRepository.existsByUserAndNameAndIdNot(user, request.getName(), id)) {
            throw new AccountAlreadyExistsException("Account already exists");
        }
        account.setName(request.getName());
        return accountRepository.save(account);

    }

    public void deleteAccount(Long id) {
        User user = getAuthenticatedUser();
        Account account = accountRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        accountRepository.delete(account);
    }

    // Helpers

    private User getAuthenticatedUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
