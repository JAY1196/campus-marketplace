// Path: src/main/java/com/campus/marketplace/dto/ProductResponse.java

package com.campus.marketplace.dto;

import com.campus.marketplace.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ProductResponse {

    private Long id;
    private String title;
    private String description;
    private Double price;
    private Product.Category category;
    private Product.Condition condition;
    private Boolean isSold;
    private LocalDateTime createdAt;
    private String sellerName;   // just the name, not the whole User object
    private String sellerEmail;

    // Converts Entity -> DTO
    public static ProductResponse fromEntity(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(product.getCategory())
                .condition(product.getCondition())
                .isSold(product.getIsSold())
                .createdAt(product.getCreatedAt())
                .sellerName(product.getSeller().getName())
                .sellerEmail(product.getSeller().getEmail())
                .build();
    }
}