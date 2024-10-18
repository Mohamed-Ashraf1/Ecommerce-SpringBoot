package com.example.ecommerce.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "orders")
@Setter
@Getter
@NoArgsConstructor
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="total_price",nullable=false)
    private Double totalPrice;

    @Column(name="order_date",nullable=false)
    private LocalDate orderDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private STATUS status;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private PAYMENT paymentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable=false)
    private User user;

    @OneToMany(mappedBy = "order",fetch = FetchType.LAZY)
    private Set<OrderProductList> OrderProductList;

    public Order(Double totalPrice, LocalDate orderDate, STATUS status, PAYMENT paymentType, User user) {
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.status = status;
        this.paymentType = paymentType;
        this.user = user;
    }
    public Order(Double totalPrice, String orderDate, STATUS status, PAYMENT paymentType, User user) {
        this.totalPrice = totalPrice;
        this.orderDate = LocalDate.parse(orderDate);
        this.status = status;
        this.paymentType = paymentType;
        this.user = user;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", totalPrice=" + totalPrice +
                ", orderDate=" + orderDate +
                ", status=" + status +
                ", paymentType=" + paymentType +
                ", user_id=" + user.getId() +
                '}';
    }
}
