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
@Table(name = "user")
@Setter
@Getter
@NoArgsConstructor
public class User extends Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private double balance;

    //will be eager by default
    @OneToOne(mappedBy = "user" ,cascade = CascadeType.ALL,orphanRemoval = true)
    private Address address;

    private LocalDate dateOfBirth;
    private String phone;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<BalanceLogs> balanceLogs;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private Set<Order> orders;

    @ManyToMany(fetch=FetchType.LAZY,cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name="user_has_interests", catalog="ecommerce", joinColumns = {
            @JoinColumn(name="user_id", nullable=false) }, inverseJoinColumns = {
            @JoinColumn(name="interest_id", nullable=false) })
    private Set<Interest> interests;


    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private Set<UserProductCart> userProductCarts;

    public User(String name, String email, String password, GENDER gender, Double balance, LocalDate dateOfBirth, String phone) {
        super(name, email, password, gender);
        this.balance = balance;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
    }

    public User(String name, String email, String password, GENDER gender, Double balance, String dateOfBirth, String phone) {
        super(name, email, password, gender);
        this.balance = balance;
        this.dateOfBirth = LocalDate.parse( dateOfBirth );
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return  Objects.equals(id, user.id) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", balance=" + balance +
                ", street=" + address.getStreet() +
                ", city=" + address.getCity() +
                ", country=" + address.getCountry() +
                ", dateOfBirth=" + dateOfBirth +
                ", phone='" + phone + '\'' +
                '}';
    }
}
