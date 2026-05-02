package com.ecom.repository;

import com.ecom.model.CartItem;
import com.ecom.model.Product;
import com.ecom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    CartItem findByUserAndProduct(User user, Product product);

    void deleteByUserAndProduct(User user, Product prod);

    List<CartItem> findByUser(User user);

    void deleteByUser(User user);
}
