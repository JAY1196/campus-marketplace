// Path: src/main/java/com/campus/marketplace/dto/ProductFilterRequest.java

package com.campus.marketplace.dto;

import com.campus.marketplace.entity.Product;
import lombok.Data;

@Data
public class ProductFilterRequest {

    private String keyword;           // searches title + description
    private Product.Category category;
    private Product.Condition condition;
    private Double minPrice;
    private Double maxPrice;
    private String sortBy = "createdAt";   // default sort field
    private String sortDirection = "desc"; // default newest first
    private int page = 0;
    private int size = 10;
}