package com.microservice.product.service;

import com.microservice.product.request.ProductPurchaseRequest;
import com.microservice.product.request.ProductRequest;
import com.microservice.product.response.ProductPurchaseResponse;
import com.microservice.product.response.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    ProductResponse createProduct(ProductRequest productRequest);
    ProductResponse findProductById(Integer id);
    List<ProductResponse> findAllProducts();
    List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> productPurchaseRequest);

}
