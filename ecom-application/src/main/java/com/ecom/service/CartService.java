package com.ecom.service;

import com.ecom.dtos.CartItemRequest;
import com.ecom.model.CartItem;
import com.ecom.model.Product;
import com.ecom.model.User;
import com.ecom.repository.CartItemRepository;
import com.ecom.repository.ProductRepository;
import com.ecom.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
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

        CartItem existingCartItem = cartItemRepository.findByUserAndProduct(user,prod);
        if(existingCartItem != null){
            existingCartItem.setQuantity(existingCartItem.getQuantity()+request.getQuantity());
            existingCartItem.setPrice(prod.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            cartItemRepository.save(existingCartItem);
        }else{
            CartItem cartItem = new CartItem();
            cartItem.setProduct(prod);
            cartItem.setUser(user);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(prod.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItemRepository.save(cartItem);

        }
        return true;
    }

    public boolean deleteItemFromCart(String userId, Long productId) {
        Optional<Product>product = productRepository.findById(productId);
        Optional<User>userOpt = userRepository.findById(Long.valueOf(userId));
        if(product.isPresent() && userOpt.isPresent()){
            cartItemRepository.deleteByUserAndProduct(userOpt.get(),product.get());
            return true;
        }
        return false;
    }

    public List<CartItem> getCartItems(String userId) {
        return userRepository.findById(Long.valueOf(userId)).
                map(cartItemRepository::findByUser)
                .orElseGet(List::of);
    }

    public void clearCart(String userId) {
        userRepository.findById(Long.valueOf(userId)).ifPresent(
                cartItemRepository::deleteByUser
        );
    }
}
