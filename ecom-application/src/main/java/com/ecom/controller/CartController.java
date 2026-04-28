package com.ecom.controller;

import com.ecom.dtos.CartItemRequest;
import com.ecom.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
