package com.example.capston1.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {

    @NotEmpty(message = "ID cannot be empty")
    private String id;

    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 4, message = "Name length must be mor than 3")
    private String name;

    @NotNull(message = "Price cannot be empty")
    @Positive(message = "Price must be a positive number")
    private double price;

    @NotEmpty(message = "Category ID cannot be empty")
    private String categoryId;
}
