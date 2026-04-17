package com.cristiancid.expensetracker.controller;

import com.cristiancid.expensetracker.dto.CreateTransactionRequest;
import com.cristiancid.expensetracker.dto.TransactionResponse;
import com.cristiancid.expensetracker.dto.UpdateTransactionRequest;
import com.cristiancid.expensetracker.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@Valid @RequestBody CreateTransactionRequest request) {
        return new ResponseEntity<>(transactionService.createTransaction(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<TransactionResponse>> getTransactions(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(transactionService.getTransactions(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponse> updateTransaction(@PathVariable Long id,
                                                                 @Valid @RequestBody UpdateTransactionRequest request) {
        return ResponseEntity.ok(transactionService.updateTransaction(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}
