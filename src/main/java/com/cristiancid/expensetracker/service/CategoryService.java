package com.cristiancid.expensetracker.service;

import com.cristiancid.expensetracker.dto.CreateCategoryRequest;
import com.cristiancid.expensetracker.dto.UpdateCategoryRequest;
import com.cristiancid.expensetracker.exception.CategoryAlreadyExistsException;
import com.cristiancid.expensetracker.exception.CategoryNotFoundException;
import com.cristiancid.expensetracker.model.Category;
import com.cristiancid.expensetracker.model.User;
import com.cristiancid.expensetracker.repository.CategoryRepository;
import com.cristiancid.expensetracker.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public CategoryService(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public Category createCategory(CreateCategoryRequest request) {
        User user = getAuthenticatedUser();

        if (categoryRepository.existsByUserAndNameAndType(user,
                request.getName(), request.getType())) {
            throw new CategoryAlreadyExistsException("Category already exists");
        }
        Category newCategory = new Category(request.getName(), request.getType(), user);
        return categoryRepository.save(newCategory);
    }

    public Page<Category> getCategories(Pageable pageable) {
        User user = getAuthenticatedUser();
        return categoryRepository.findByUser(user, pageable);
    }

    public Category updateCategory(Long id, UpdateCategoryRequest request) {
        User user = getAuthenticatedUser();
        Category category = categoryRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        if (categoryRepository.existsByUserAndNameAndTypeAndIdNot(user, request.getName(),
                request.getType(), id)) {
            throw new CategoryAlreadyExistsException("Category already exists");
        }
        category.setName(request.getName());
        category.setType(request.getType());
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        User user = getAuthenticatedUser();
        Category category = categoryRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        categoryRepository.delete(category);
    }


    // Helpers

    private User getAuthenticatedUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
