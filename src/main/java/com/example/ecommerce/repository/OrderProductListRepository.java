package com.example.ecommerce.repository;

import com.example.ecommerce.entity.OrderProductId;
import com.example.ecommerce.entity.OrderProductList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface OrderProductListRepository extends JpaRepository<OrderProductList, OrderProductId> {
}
