package com.campus.marketplace.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @Column(length = 1000)
    private String description;

    @Positive
    private Double price;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "product_condition")
    @Enumerated(EnumType.STRING)
    private Condition condition;

    @Builder.Default
    private Boolean isSold = false;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public enum Category {
        BOOKS, ELECTRONICS, NOTES, FURNITURE, CLOTHING, OTHER
    }

    public enum Condition {
        NEW, LIKE_NEW, USED
    }
}