package com.electronicsstore.store.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    //todo refact id to product_id
    private Long id;

    @Column(name = "username")
    private String username;

    private String password;

    private String roles;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> cart;

    public void addToCart(Product product) {
        if (product.getOwner() != null) {
            product.getOwner().getCart().remove(product);
        }
        product.setOwner(this);
    }

    public void removeFromCart(Product product) {
        if (this.cart.remove(product)) {
            product.setOwner(null);
        }
    }
}
