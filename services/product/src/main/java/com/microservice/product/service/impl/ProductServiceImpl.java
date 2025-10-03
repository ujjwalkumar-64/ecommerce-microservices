package com.microservice.product.service.impl;

import com.microservice.product.exception.ProductPurchaseException;
import com.microservice.product.mapper.ProductMapper;
import com.microservice.product.repository.ProductRepository;
import com.microservice.product.request.ProductPurchaseRequest;
import com.microservice.product.request.ProductRequest;
import com.microservice.product.response.ProductPurchaseResponse;
import com.microservice.product.response.ProductResponse;
import com.microservice.product.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository  productRepository;
    @Autowired
    ProductMapper productMapper;

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        var product = productMapper.toProduct(productRequest);
        return productMapper.fromProduct(productRepository.save(product));
    }

    @Override
    public ProductResponse findProductById(Integer id) {
        return productRepository.findById(id)
                .map(productMapper:: fromProduct)
                .orElseThrow(()-> new EntityNotFoundException("Product not found with id :: " + id));
    }

    @Override
    public List<ProductResponse> findAllProducts() {
        return  productRepository.findAll()
                .stream()
                .map(productMapper::fromProduct)
                .toList();
    }

    @Override
    @Transactional(rollbackFor = ProductPurchaseException.class)
    public  List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> productPurchaseRequest){
        var productIds = productPurchaseRequest.stream().map(ProductPurchaseRequest::productId).toList();
        var products = productRepository.findAllById(productIds);
        if(products.size() != productIds.size()){
            throw new ProductPurchaseException("one or more product does not exist");
        }

        var sortedProducts = productPurchaseRequest
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();

        var purchaseProducts = new ArrayList<ProductPurchaseResponse>();
        for(int i=0;i<products.size();i++){
            var product = products.get(i);
            var productRequest = sortedProducts.get(i);
            if(product.getAvailableQuantity() < productRequest.quantity()){
                throw  new ProductPurchaseException("insufficient stock quantity for product id:: " + productRequest.productId());
            }

            var newAvailableQuantity = product.getAvailableQuantity()-productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            productRepository.save(product);
            purchaseProducts.add(productMapper.toProductPurchaseResponse(product, productRequest.quantity()));

        }
        return purchaseProducts;
    }

}
