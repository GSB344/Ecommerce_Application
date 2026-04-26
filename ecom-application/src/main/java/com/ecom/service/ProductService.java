package com.ecom.service;

import com.ecom.dtos.ProductRequest;
import com.ecom.dtos.ProductResponse;
import com.ecom.model.Product;
import com.ecom.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    public ProductResponse createProduct(ProductRequest productRequest){
        Product product = new Product();
        updateProductFromRequest(product,productRequest);
        Product prd = productRepository.save(product);
        return mapToProductResponse(prd);
    }

    private ProductResponse mapToProductResponse(Product prd) {
        ProductResponse prodRes = new ProductResponse();
        prodRes.setId(prd.getId());
        prodRes.setName(prd.getName());
        prodRes.setStockQuantity(prd.getStockQuantity());
        prodRes.setCategory(prd.getCategory());
        prodRes.setDescription(prd.getDescription());
        prodRes.setPrice(prd.getPrice());
        prodRes.setImageUrl(prd.getImageUrl());
        prodRes.setActive(prd.getActive());
        return prodRes;
    }

    public void updateProductFromRequest(Product product , ProductRequest productRequest){
        product.setName(productRequest.getName());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setCategory(productRequest.getCategory());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setImageUrl(productRequest.getImageUrl());
    }

    public Optional<ProductResponse> updateProduct(Long id, ProductRequest productRequest) {
        return productRepository.findById(id)
                .map(existingProduct->{
                    updateProductFromRequest(existingProduct,productRequest);
                    Product savedProduct = productRepository.save(existingProduct);
                    return mapToProductResponse(savedProduct);
                });
    }

    public List<ProductResponse> getAllProduct() {
        return productRepository.findByActiveTrue()
                .stream().map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    public boolean deleteProduct(Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setActive(false);
                    productRepository.save(product);
                    return true;
                }).orElse(false);
    }

    public List<ProductResponse> searchProducts(String keyword) {
        return productRepository.searchProduct(keyword).stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }
}
