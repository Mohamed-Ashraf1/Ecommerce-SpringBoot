package com.example.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;


@Entity
@Table(name= "address")
@Getter
@Setter
@NoArgsConstructor
public class Address implements Serializable {

    @Id
    private Integer id;

    @Column(nullable=false)
    private String street;

    @Column(nullable=false)
    private String city;

    @Column(nullable=false)
    private String country;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(nullable = false)
    private User user;

    public Address(String street, String city, String country, User user) {
        this.street = street;
        this.city = city;
        this.country = country;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
