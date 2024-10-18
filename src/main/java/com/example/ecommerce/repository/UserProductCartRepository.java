package com.example.ecommerce.repository;

import com.example.ecommerce.entity.User;
import com.example.ecommerce.entity.UserProductCart;
import com.example.ecommerce.entity.UserProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserProductCartRepository extends JpaRepository<UserProductCart, UserProductId> {
    @Query("select u from UserProductCart u where u.user=?1")
    List<UserProductCart> getCartByUser(User user);

    @Query("delete  from UserProductCart where user.id=?1 and product.id=?2")
    void deleteProductFromCart(int userId, int productId);

    void deleteAllProductsFromCartByUser(User user);
}
