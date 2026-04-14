package com.cristiancid.expensetracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateAccountRequest {

    @NotBlank(message = "name cannot be blank")
    @Size(min = 3, max = 30, message = "name must be between 3 and 30 characters")
    private String name;

    public UpdateAccountRequest() {}

    public UpdateAccountRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}