package com.ecom.service;

import com.ecom.dtos.CartItemRequest;
import com.ecom.model.CartIem;
import com.ecom.model.Product;
import com.ecom.model.User;
import com.ecom.repository.CartItemRepository;
import com.ecom.repository.ProductRepository;
import com.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Optional;

@RequiredArgsConstructor
public class CartService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    public boolean addToCart(String userId, CartItemRequest request) {
        Optional<Product>product = productRepository.findById(request.getProductId());
        if(product.isEmpty()){
            return false;
        }

        Product prod = product.get();
        if(prod.getStockQuantity()<request.getQuantity()){
            return false;
        }

        Optional<User>userOpt = userRepository.findById(Long.valueOf(userId));
        if(userOpt.isEmpty()){
            return false;
        }

        User user = userOpt.get();

        CartIem existingCartIem = cartItemRepository.findByUserAndProduct(user,prod);
        if(existingCartIem != null){
            existingCartIem.setQuantity(existingCartIem.getQuantity()+request.getQuantity());
            existingCartIem.setPrice(prod.getPrice().multiply(BigDecimal.valueOf(existingCartIem.getQuantity())));
            cartItemRepository.save(existingCartIem);
        }else{
            CartIem cartIem = new CartIem();
            cartIem.setProduct(prod);
            cartIem.setUser(user);
            cartIem.setQuantity(request.getQuantity());
            cartIem.setPrice(prod.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItemRepository.save(cartIem);

        }
        return true;
    }
}
