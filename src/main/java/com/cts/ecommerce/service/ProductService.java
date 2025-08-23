package com.cts.ecommerce.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.cts.ecommerce.model.Category;
import com.cts.ecommerce.model.Product;
import com.cts.ecommerce.repository.CategoryRepository;
import com.cts.ecommerce.repository.ProductRepository;
import com.cts.ecommerce.exception.ResourceNotFoundException;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    // ✅ Use constructor injection for both
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Product createProduct(Product product, Long categoryId) {
        // ✅ Check if category exists
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category with ID " + categoryId + " not found"));

        // ✅ Set category in product
        product.setCategory(category);

        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product with ID " + id + " not found"));
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}

