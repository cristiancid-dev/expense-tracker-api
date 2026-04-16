package com.cristiancid.expensetracker.repository;

import com.cristiancid.expensetracker.model.Account;
import com.cristiancid.expensetracker.model.Transaction;
import com.cristiancid.expensetracker.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findByUser(User user, Pageable pageable);

    Page<Transaction> findByUserAndAccount(User user, Account account, Pageable pageable);

    Optional<Transaction> findByIdAndUser(Long id, User user);

}
