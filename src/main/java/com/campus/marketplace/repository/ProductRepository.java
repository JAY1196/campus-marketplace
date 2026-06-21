// Path: src/main/java/com/campus/marketplace/repository/ProductRepository.java

package com.campus.marketplace.repository;

import com.campus.marketplace.entity.Product;
import com.campus.marketplace.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findBySeller(User seller);

    List<Product> findByIsSoldFalse();

    List<Product> findByCategory(Product.Category category);
}