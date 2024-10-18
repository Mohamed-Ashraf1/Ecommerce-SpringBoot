package com.example.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name="interest")
@Setter
@Getter
@NoArgsConstructor
public class Interest implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable=false)
    private String name;

    @ManyToMany(fetch=FetchType.LAZY,cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name="user_has_interests", catalog="ecommerce", joinColumns = {
            @JoinColumn(name="interest_id", nullable=false) }, inverseJoinColumns = {
            @JoinColumn(name="user_id", nullable=false) })
    private Set<User> users;

    public Interest(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Interest{" +
                "id=" + id +
                ", name='" + name +
                '}';
    }
}
