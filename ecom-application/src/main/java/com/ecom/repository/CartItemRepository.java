package com.ecom.repository;

import com.ecom.model.CartIem;
import com.ecom.model.Product;
import com.ecom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartIem,Long> {

    CartIem findByUserAndProduct(User user, Product product);
}
