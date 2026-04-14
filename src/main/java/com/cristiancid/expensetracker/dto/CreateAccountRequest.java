package com.cristiancid.expensetracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateAccountRequest {

    @NotBlank(message = "name cannot be blank")
    @Size(min = 3, max = 30, message = "name must be between 3 and 30 characters")
    private String name;

    public CreateAccountRequest() {}

    public CreateAccountRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
