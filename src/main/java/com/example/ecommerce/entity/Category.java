package com.example.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;


@Entity
@Table(name = "category")
@Setter
@Getter
@NoArgsConstructor
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_category")
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory",fetch = FetchType.LAZY)
    private Set<Category> childCategories ;

    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY)
    private Set<Product> products;


}
