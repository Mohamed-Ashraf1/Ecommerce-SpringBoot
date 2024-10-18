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
public class UserProductId  implements Serializable {

    @Column(name = "user_id", nullable = false)
    private Integer userId;
    @Column(name = "product_id", nullable = false)
    private int productId;


    public UserProductId(int userId, int productId) {
        this.userId = userId;
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProductId that = (UserProductId) o;
        return userId == that.userId && productId == that.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, productId);
    }
}
