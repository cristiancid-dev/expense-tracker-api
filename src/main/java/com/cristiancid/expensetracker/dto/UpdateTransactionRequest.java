package com.cristiancid.expensetracker.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UpdateTransactionRequest {

    @NotNull(message = "amount cannot be null")
    @DecimalMin(value = "0.01", message = "amount must be greater than 0")
    private BigDecimal amount;

    @NotBlank(message = "description cannot be blank")
    @Size(min = 5, max = 100)
    private String description;

    @NotNull(message = "accountId cannot be null")
    private Long accountId;

    @NotNull(message = "categoryId cannot be null")
    private Long categoryId;

    @NotNull(message = "transactionDate cannot be null")
    private LocalDate transactionDate;


    public UpdateTransactionRequest() {}

    public UpdateTransactionRequest(BigDecimal amount, String description, Long accountId, Long categoryId, LocalDate transactionDate) {
        this.amount = amount;
        this.description = description;
        this.accountId = accountId;
        this.categoryId = categoryId;
        this.transactionDate = transactionDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public Long getAccountId() {
        return accountId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }
}
