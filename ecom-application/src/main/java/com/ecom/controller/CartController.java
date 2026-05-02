package com.ecom.controller;

import com.ecom.dtos.CartItemRequest;
import com.ecom.model.CartItem;
import com.ecom.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/cart")
public class CartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String>addToCart(
            @RequestHeader("X-User-ID") String userId,
            @RequestBody CartItemRequest request) {
        if(!cartService.addToCart(userId , request)){
            return ResponseEntity.badRequest().body("Product out of stock or user not found");
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void>removeFromCart(
            @RequestHeader("X-User-ID") String userId,
            @PathVariable Long productId){
        boolean deleted = cartService.deleteItemFromCart(userId,productId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<CartItem>>getCartItems(
            @RequestHeader("X-User-ID") String userId){
        List<CartItem> cartItems = cartService.getCartItems(userId);
        return ResponseEntity.ok(cartItems);
    }
}
