package com.phatdo.ecommerce.arena.account.domain;

import com.phatdo.ecommerce.arena.customer.domain.Customer;
import com.phatdo.ecommerce.arena.seller.domain.Seller;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
@Entity
public class Account {
    @Id
    @Column(unique = true)
    private final UUID uuid = new UUID(1, UUID.randomUUID().getMostSignificantBits());

    @Column(unique = true)
    private final String email;

    private String fullName;

    private String password;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private Customer customer;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Seller> sellers = new ArrayList<>();

    @Transient
    private Role role;
}
