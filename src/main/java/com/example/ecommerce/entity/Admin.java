package com.example.ecommerce.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Entity
@Table(name="admin")
@Getter
@Setter
@NoArgsConstructor
public class Admin extends Person implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    public Admin(String name, String email, String password, GENDER gender) {
        super(name, email, password, gender);
    }
}
