package com.cristiancid.expensetracker.service;

import com.cristiancid.expensetracker.dto.CreateTransactionRequest;
import com.cristiancid.expensetracker.dto.TransactionResponse;
import com.cristiancid.expensetracker.dto.UpdateTransactionRequest;
import com.cristiancid.expensetracker.exception.AccountNotFoundException;
import com.cristiancid.expensetracker.exception.CategoryNotFoundException;
import com.cristiancid.expensetracker.exception.TransactionNotFoundException;
import com.cristiancid.expensetracker.model.Account;
import com.cristiancid.expensetracker.model.Category;
import com.cristiancid.expensetracker.model.Transaction;
import com.cristiancid.expensetracker.model.User;
import com.cristiancid.expensetracker.model.enums.CategoryType;
import com.cristiancid.expensetracker.repository.AccountRepository;
import com.cristiancid.expensetracker.repository.CategoryRepository;
import com.cristiancid.expensetracker.repository.TransactionRepository;
import com.cristiancid.expensetracker.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository,
                              CategoryRepository categoryRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public TransactionResponse createTransaction(CreateTransactionRequest request) {
        User user = getAuthenticatedUser();
        Account account = accountRepository.findByIdAndUser(request.getAccountId(), user)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        Category category = categoryRepository.findByIdAndUser(request.getCategoryId(), user)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        CategoryType type = category.getType();
        Transaction newTransaction = new Transaction(request.getAmount(), request.getDescription(),
                request.getTransactionDate(), user, account, category, type);
        Transaction savedTransaction = transactionRepository.save(newTransaction);
        return mapToResponse(savedTransaction);
    }

    public Page<TransactionResponse> getTransactions(Pageable pageable) {
        User user = getAuthenticatedUser();
        Page<Transaction> transactions = transactionRepository.findByUser(user,pageable);
        return transactions.map(this::mapToResponse);
    }

    public TransactionResponse getTransactionById(Long id) {
        User user = getAuthenticatedUser();
        Transaction transaction = transactionRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));
        return mapToResponse(transaction);
    }

    public TransactionResponse updateTransaction(Long id, UpdateTransactionRequest request) {
        User user = getAuthenticatedUser();
        Category category = categoryRepository.findByIdAndUser(request.getCategoryId(), user)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        Account account = accountRepository.findByIdAndUser(request.getAccountId(), user)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        Transaction transaction = transactionRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));
        transaction.setAmount(request.getAmount());
        transaction.setDescription(request.getDescription());
        transaction.setDate(request.getTransactionDate());
        transaction.setCategory(category);
        transaction.setType(category.getType());
        transaction.setAccount(account);
        Transaction updatedTransaction = transactionRepository.save(transaction);
        return mapToResponse(updatedTransaction);
    }

    public void deleteTransaction(Long id) {
        User user = getAuthenticatedUser();
        Transaction transaction = transactionRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));
        transactionRepository.delete(transaction);
    }

    // Helpers

    private User getAuthenticatedUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private TransactionResponse mapToResponse(Transaction transaction) {
        Long id = transaction.getId();
        BigDecimal amount = transaction.getAmount();
        String description = transaction.getDescription();
        LocalDate date = transaction.getDate();
        CategoryType type = transaction.getType();
        String accountName = transaction.getAccount().getName();
        String categoryName = transaction.getCategory().getName();
        return new TransactionResponse(id,amount,description,date, type,
                accountName, categoryName);
    }
}