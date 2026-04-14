package com.cristiancid.expensetracker.repository;

import com.cristiancid.expensetracker.model.Account;
import com.cristiancid.expensetracker.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Page<Account> findByUser(User user, Pageable pageable);

    boolean existsByUserAndName(User user, String name);

    Optional<Account> findByIdAndUser(Long id, User user);

    boolean existsByUserAndNameAndIdNot(User user, String name, Long id);
}
