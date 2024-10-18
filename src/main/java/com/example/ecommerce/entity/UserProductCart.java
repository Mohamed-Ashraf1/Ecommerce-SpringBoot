package com.example.ecommerce.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "user_product_cart")
@Setter
@Getter
@NoArgsConstructor
public class UserProductCart  implements Serializable {


    @EmbeddedId
    @AttributeOverrides( {
            @AttributeOverride(name="userId", column=@Column(name="user_id", nullable=false,insertable=false, updatable=false) ),
            @AttributeOverride(name="productId", column=@Column(name="product_id", nullable=false,insertable=false, updatable=false) ) } )
    private UserProductId userProductId = new UserProductId();

    @Column(name="product_quantity", nullable=false)
    private Integer productQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(nullable = false)
    private User user;

    public UserProductCart(User user, Product product, Integer productQuantity) {
        this.user = user;
        this.product = product;
        this.productQuantity = productQuantity;
    }
}
