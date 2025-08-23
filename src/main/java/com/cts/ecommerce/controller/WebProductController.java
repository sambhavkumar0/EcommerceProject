package com.cts.ecommerce.controller;

import com.cts.ecommerce.model.Product;
import com.cts.ecommerce.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class WebProductController {

    private final ProductService service;

    public WebProductController(ProductService service) {
        this.service = service;
    }

    // ✅ Show all products
    @GetMapping
    public String listProducts(Model model) {
        List<Product> products = service.getAllProducts();
        model.addAttribute("products", products);
        return "products"; // renders products.html
    }

    // ✅ Show product creation form
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        return "product-form"; // renders product-form.html
    }
    
    
    //Get product by ID
    @GetMapping("/{id}")
    public String getProductById(@PathVariable Long id, Model model) {
        Product product = service.getProductById(id);
        if (product == null) {
            // optional: handle not found gracefully
            return "error"; 
        }
        model.addAttribute("product", product);
        return "product-details"; // renders product-details.html
    }

    // ✅ Handle form submission (POST)
    @PostMapping
    public String saveProduct(@ModelAttribute("product") Product product,
                              @RequestParam("categoryId") Long categoryId) {
        service.createProduct(product, categoryId);
        return "redirect:/products"; // redirect to list page
    }

    // ✅ Delete product
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        service.deleteProduct(id);
        return "redirect:/products";
    }
}
