// Path: src/main/java/com/campus/marketplace/specification/ProductSpecification.java

package com.campus.marketplace.specification;

import com.campus.marketplace.dto.ProductFilterRequest;
import com.campus.marketplace.entity.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    public static Specification<Product> withFilters(ProductFilterRequest filter) {

        return (root, query, criteriaBuilder) -> {

            var predicates = criteriaBuilder.conjunction(); // starts as "always true"

            // Only show available (not sold) products
            predicates = criteriaBuilder.and(predicates,
                    criteriaBuilder.isFalse(root.get("isSold")));

            // Keyword search — matches title OR description, case-insensitive
            if (filter.getKeyword() != null && !filter.getKeyword().isBlank()) {
                String likePattern = "%" + filter.getKeyword().toLowerCase() + "%";
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.or(
                                criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), likePattern),
                                criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), likePattern)
                        ));
            }

            // Category filter — only applied if provided
            if (filter.getCategory() != null) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.equal(root.get("category"), filter.getCategory()));
            }

            // Condition filter — only applied if provided
            if (filter.getCondition() != null) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.equal(root.get("condition"), filter.getCondition()));
            }

            // Min price — only applied if provided
            if (filter.getMinPrice() != null) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.greaterThanOrEqualTo(root.get("price"), filter.getMinPrice()));
            }

            // Max price — only applied if provided
            if (filter.getMaxPrice() != null) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.lessThanOrEqualTo(root.get("price"), filter.getMaxPrice()));
            }

            return predicates;
        };
    }
}