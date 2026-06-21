// Path: src/main/java/com/campus/marketplace/service/ProductService.java

package com.campus.marketplace.service;

import com.campus.marketplace.dto.ProductRequest;
import com.campus.marketplace.dto.ProductResponse;
import com.campus.marketplace.entity.Product;
import com.campus.marketplace.entity.User;
import com.campus.marketplace.exception.ResourceNotFoundException;
import com.campus.marketplace.repository.ProductRepository;
import com.campus.marketplace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    // CREATE
    public ProductResponse createProduct(ProductRequest request, String sellerEmail) {
        User seller = userRepository.findByEmail(sellerEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + sellerEmail));

        Product product = Product.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .price(request.getPrice())
                .category(request.getCategory())
                .condition(request.getCondition())
                .isSold(false)
                .seller(seller)
                .build();

        Product saved = productRepository.save(product);
        return ProductResponse.fromEntity(saved);
    }

    // READ - all available products
    public List<ProductResponse> getAllProducts() {
        return productRepository.findByIsSoldFalse()
                .stream()
                .map(ProductResponse::fromEntity)
                .collect(Collectors.toList());
    }

    // READ - single product by id
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return ProductResponse.fromEntity(product);
    }

    // READ - logged-in user's own listings
    public List<ProductResponse> getMyListings(String sellerEmail) {
        User seller = userRepository.findByEmail(sellerEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + sellerEmail));

        return productRepository.findBySeller(seller)
                .stream()
                .map(ProductResponse::fromEntity)
                .collect(Collectors.toList());
    }

    // UPDATE
    public ProductResponse updateProduct(Long id, ProductRequest request, String requesterEmail) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        // Ownership check — only the seller who posted it can edit it
        if (!product.getSeller().getEmail().equals(requesterEmail)) {
            throw new AccessDeniedException("You can only edit your own listings");
        }

        product.setTitle(request.getTitle());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());
        product.setCondition(request.getCondition());

        Product updated = productRepository.save(product);
        return ProductResponse.fromEntity(updated);
    }

    // DELETE
    public void deleteProduct(Long id, String requesterEmail) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        if (!product.getSeller().getEmail().equals(requesterEmail)) {
            throw new AccessDeniedException("You can only delete your own listings");
        }

        productRepository.delete(product);
    }

    // MARK AS SOLD
    public ProductResponse markAsSold(Long id, String requesterEmail) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        if (!product.getSeller().getEmail().equals(requesterEmail)) {
            throw new AccessDeniedException("You can only update your own listings");
        }

        product.setIsSold(true);
        Product updated = productRepository.save(product);
        return ProductResponse.fromEntity(updated);
    }
}