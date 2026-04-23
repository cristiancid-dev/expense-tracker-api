package com.cristiancid.expensetracker.repository;

import com.cristiancid.expensetracker.model.Account;
import com.cristiancid.expensetracker.model.Transaction;
import com.cristiancid.expensetracker.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findByUser(User user, Pageable pageable);

    Page<Transaction> findByUserAndAccount(User user, Account account, Pageable pageable);

    Optional<Transaction> findByIdAndUser(Long id, User user);

    @Query(value = "SELECT COALESCE(" +
            "SUM(" +
                "CASE " +
                    "WHEN t.type = com.cristiancid.expensetracker.model.enums.CategoryType.INCOME " +
                    "THEN t.amount " +
                    "ELSE -t.amount " +
                "END " +
            "), " +
            "0) " +
            "FROM Transaction t " +
            "WHERE t.account = :account" )
    BigDecimal calculateAccountBalance(@Param("account") Account account);
}
