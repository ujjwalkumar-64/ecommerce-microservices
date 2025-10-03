package com.microservice.product.controller;

import com.microservice.product.request.ProductPurchaseRequest;
import com.microservice.product.request.ProductRequest;
import com.microservice.product.response.ProductPurchaseResponse;
import com.microservice.product.response.ProductResponse;
import com.microservice.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        return ResponseEntity.ok(productService.createProduct(productRequest));
    }

    @PostMapping("/purchase")
    public ResponseEntity<List<ProductPurchaseResponse>>  createProductPurchase(@RequestBody List<ProductPurchaseRequest> productPurchaseRequest) {
        return ResponseEntity.ok(productService.purchaseProducts(productPurchaseRequest));
    }

    @GetMapping("{product-id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("product-id") Integer productId) {
        return ResponseEntity.ok(productService.findProductById(productId));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.findAllProducts());
    }

}
