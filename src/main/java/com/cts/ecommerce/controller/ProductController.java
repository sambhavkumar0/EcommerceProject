package com.cts.ecommerce.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cts.ecommerce.model.Product;
import com.cts.ecommerce.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // ✅ GET all products
    @GetMapping
    public List<Product> getAllProducts() {
        return service.getAllProducts();
    }

    // ✅ GET product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        // Service will throw ResourceNotFoundException if not found
        return ResponseEntity.ok(service.getProductById(id));
    }

    // ✅ POST create product
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setImageUrl(request.getImageUrl());

        Product savedProduct = service.createProduct(product, request.getCategoryId());
        return ResponseEntity.ok(savedProduct);
    }

    // ✅ DELETE product
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        // Service will throw ResourceNotFoundException if not found
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ DTO for product creation
    public static class ProductRequest {
        private String name;
        private String description;
        private java.math.BigDecimal price;
        private Integer stockQuantity;
        private Long categoryId;
        private String imageUrl;

        // Getters & setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public java.math.BigDecimal getPrice() { return price; }
        public void setPrice(java.math.BigDecimal price) { this.price = price; }
        public Integer getStockQuantity() { return stockQuantity; }
        public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }
        public Long getCategoryId() { return categoryId; }
        public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
        public String getImageUrl() { return imageUrl; }
        public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    }
}
