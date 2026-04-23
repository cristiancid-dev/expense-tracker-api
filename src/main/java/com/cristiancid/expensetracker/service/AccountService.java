package com.cristiancid.expensetracker.service;

import com.cristiancid.expensetracker.dto.AccountResponse;
import com.cristiancid.expensetracker.dto.CreateAccountRequest;
import com.cristiancid.expensetracker.dto.UpdateAccountRequest;
import com.cristiancid.expensetracker.exception.AccountAlreadyExistsException;
import com.cristiancid.expensetracker.exception.AccountNotFoundException;
import com.cristiancid.expensetracker.model.Account;
import com.cristiancid.expensetracker.model.User;
import com.cristiancid.expensetracker.repository.AccountRepository;
import com.cristiancid.expensetracker.repository.TransactionRepository;
import com.cristiancid.expensetracker.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository,
                          TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    public AccountResponse createAccount(CreateAccountRequest request) {
        User user = getAuthenticatedUser();
        if (accountRepository.existsByUserAndName(user,request.getName())) {
            throw new AccountAlreadyExistsException("Account already exists");
        }
        Account newAccount = new Account(request.getName(), user);
        Account savedAccount = accountRepository.save(newAccount);
        return mapToResponse(savedAccount);
    }

    public Page<AccountResponse> getAccounts(Pageable pageable) {
        User user = getAuthenticatedUser();
        Page<Account> accounts = accountRepository.findByUser(user, pageable);
        return accounts.map(this::mapToResponse);
    }

    public AccountResponse getAccountById(Long id) {
        User user = getAuthenticatedUser();
        Account account = accountRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        return mapToResponse(account);
    }

    public AccountResponse updateAccount(Long id, UpdateAccountRequest request) {
        User user = getAuthenticatedUser();
        Account account = accountRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        if (accountRepository.existsByUserAndNameAndIdNot(user, request.getName(), id)) {
            throw new AccountAlreadyExistsException("Account already exists");
        }
        account.setName(request.getName());
        Account updatedAccount =  accountRepository.save(account);
        return mapToResponse(updatedAccount);

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

    private AccountResponse mapToResponse(Account account) {
        Long id = account.getId();
        String name = account.getName();
        BigDecimal balance = transactionRepository.calculateAccountBalance(account);
        return new AccountResponse(id, name, balance);
    }
}
