package com.example.ecommerce.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "order_product_list")
@Setter
@Getter
@NoArgsConstructor
public class OrderProductList  implements Serializable {

    @EmbeddedId
    @AttributeOverrides( {
            @AttributeOverride(name="orderId", column=@Column(name="order_id", nullable=false,insertable=false, updatable=false) ),
            @AttributeOverride(name="productId", column=@Column(name="product_id", nullable=false,insertable=false, updatable=false) ) } )
    private OrderProductId orderProductId = new OrderProductId();

    @Column(name="product_quantity",nullable=false)
    private Integer productQuantity;

    @Column(name="product_price",nullable = false)
    private Double productPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    @JoinColumn(nullable=false)
    private Order order;


    public OrderProductList(Integer productQuantity, Double productPrice, Product product, Order order) {
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
        this.product = product;
        this.order = order;
    }
}
