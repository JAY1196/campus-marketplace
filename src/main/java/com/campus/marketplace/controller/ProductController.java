// Path: src/main/java/com/campus/marketplace/controller/ProductController.java

package com.campus.marketplace.controller;

import com.campus.marketplace.dto.ProductRequest;
import com.campus.marketplace.dto.ProductResponse;
import com.campus.marketplace.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // POST /api/products — create a new listing
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @Valid @RequestBody ProductRequest request,
            Authentication authentication) {

        String sellerEmail = authentication.getName(); // comes from JWT via SecurityContext
        ProductResponse response = productService.createProduct(request, sellerEmail);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET /api/products — view all available listings (public)
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // GET /api/products/{id} — view one listing
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    // GET /api/products/my-listings — logged-in user's own products
    @GetMapping("/my-listings")
    public ResponseEntity<List<ProductResponse>> getMyListings(Authentication authentication) {
        return ResponseEntity.ok(productService.getMyListings(authentication.getName()));
    }

    // PUT /api/products/{id} — edit a listing
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request,
            Authentication authentication) {

        ProductResponse response = productService.updateProduct(id, request, authentication.getName());
        return ResponseEntity.ok(response);
    }

    // DELETE /api/products/{id} — delete a listing
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id, Authentication authentication) {
        productService.deleteProduct(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }

    // PATCH /api/products/{id}/sold — mark as sold
    @PatchMapping("/{id}/sold")
    public ResponseEntity<ProductResponse> markAsSold(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(productService.markAsSold(id, authentication.getName()));
    }
}