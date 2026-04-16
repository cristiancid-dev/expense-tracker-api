package com.cristiancid.expensetracker.dto;

import com.cristiancid.expensetracker.model.enums.CategoryType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionResponse {

    private Long id;
    private BigDecimal amount;
    private String description;
    private LocalDate date;
    private CategoryType type;
    private String accountName;
    private String categoryName;

    public TransactionResponse() {}

    public TransactionResponse(Long id, BigDecimal amount, String description, LocalDate date,
                               CategoryType type, String accountName, String categoryName) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.type = type;
        this.accountName = accountName;
        this.categoryName = categoryName;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    public CategoryType getType() {
        return type;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setType(CategoryType type) {
        this.type = type;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
