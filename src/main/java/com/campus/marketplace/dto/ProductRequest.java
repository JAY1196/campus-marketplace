// Path: src/main/java/com/campus/marketplace/dto/ProductRequest.java

package com.campus.marketplace.dto;

import com.campus.marketplace.entity.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProductRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    private Double price;

    @NotNull(message = "Category is required")
    private Product.Category category;

    @NotNull(message = "Condition is required")
    private Product.Condition condition;
}