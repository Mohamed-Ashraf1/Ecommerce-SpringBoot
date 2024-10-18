package com.example.ecommerce.repository;

import com.example.ecommerce.entity.UserProductCart;
import com.example.ecommerce.entity.UserProductId;
import org.springframework.data.repository.CrudRepository;

public interface UserProductCartRepository extends CrudRepository<UserProductCart, UserProductId> {

}
