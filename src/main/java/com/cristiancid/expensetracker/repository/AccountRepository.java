package com.cristiancid.expensetracker.repository;

import com.cristiancid.expensetracker.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
