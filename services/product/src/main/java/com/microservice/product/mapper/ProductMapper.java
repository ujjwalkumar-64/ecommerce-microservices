package com.microservice.product.mapper;

import com.microservice.product.entity.Category;
import com.microservice.product.entity.Product;
import com.microservice.product.request.ProductRequest;
import com.microservice.product.response.ProductPurchaseResponse;
import com.microservice.product.response.ProductResponse;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    public Product toProduct(ProductRequest productRequest) {
        return Product.builder()
                .id(productRequest.id())
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .availableQuantity(productRequest.availableQuantity())
                .category(
                    Category.builder()
                            .id(productRequest.categoryId())
                            .build()
                )
                .build();
    }

    public ProductResponse fromProduct(Product product) {
        return   ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .availableQuantity(product.getAvailableQuantity())
                .categoryId(product.getCategory().getId())
                .categoryName(product.getCategory().getName())
                .categoryDescription(product.getCategory().getDescription())

                .build();
    }

    public ProductPurchaseResponse toProductPurchaseResponse(Product product, double quantity) {
        return    ProductPurchaseResponse.builder()
                .productId(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(quantity)
                .build();
    }
}
