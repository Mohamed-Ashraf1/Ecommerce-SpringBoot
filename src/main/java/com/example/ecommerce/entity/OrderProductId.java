package com.example.ecommerce.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Setter
@Getter
@NoArgsConstructor
public class OrderProductId implements Serializable {
    @Column(name = "order_id", nullable=false)
    private int orderId;
    @Column(name= "product_id", nullable=false)
    private int productId;

    public OrderProductId(int orderId, int productId) {
        this.orderId = orderId;
        this.productId = productId;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderProductId that = (OrderProductId) o;
        return orderId == that.orderId && productId == that.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId);
    }
}
