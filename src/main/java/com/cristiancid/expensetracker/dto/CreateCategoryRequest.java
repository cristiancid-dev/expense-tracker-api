package com.cristiancid.expensetracker.dto;

import com.cristiancid.expensetracker.model.enums.CategoryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateCategoryRequest {

    @NotBlank(message = "name cannot be blank")
    @Size(min = 3, max = 20, message = "name must be between 3 and 20 characters")
    private String name;

    @NotNull(message = "type cannot be null")
    private CategoryType type;

    public CreateCategoryRequest() {}

    public CreateCategoryRequest(String name, CategoryType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public CategoryType getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(CategoryType type) {
        this.type = type;
    }
}
