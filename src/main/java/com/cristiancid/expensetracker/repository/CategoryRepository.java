package com.cristiancid.expensetracker.repository;

import com.cristiancid.expensetracker.model.Category;
import com.cristiancid.expensetracker.model.User;
import com.cristiancid.expensetracker.model.enums.CategoryType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Page<Category> findByUser(User user, Pageable pageable);

    boolean existsByUserAndNameAndType(User user, String name, CategoryType type);

    Optional<Category> findByIdAndUser(Long id, User user);
}
