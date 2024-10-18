package com.example.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name="product")
@Setter
@Getter
@NoArgsConstructor
public class Product implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="product_name",nullable = false,unique = true)
    private String productName;
    private String description;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private Double price;
    private String image;
    private boolean isDeleted;

    public Product(String productName, Integer quantity, Double price, Category category) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
    }

    public Product(String productName,String description, Integer quantity, Double price, Category category,String image) {
        this.productName = productName;
        this.description= description;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
        this.image=image;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable=false)
    private Category category;

    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY)
    private Set<OrderProductList> OrderProductList;

    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY)
    private Set<UserProductCart> userProductCarts;


    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", category=" + category.getName() +
                '}';
    }
}
