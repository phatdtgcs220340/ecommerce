package com.phatdo.ecommerce.arena.account.domain;

import com.phatdo.ecommerce.arena.seller.domain.AccountSeller;
import com.phatdo.ecommerce.arena.customer.domain.Customer;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
@Entity
public class Account implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(unique = true, nullable = false)
    private UUID uuid;

    @Column(unique = true)
    private final String email;

    private String password;

    @OneToOne(mappedBy = "account",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Customer customer;

    @OneToMany(mappedBy = "account",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private final Set<AccountSeller> sellers = new HashSet<>();

    @Transient
    private Role role;

    public enum Role {
        CUSTOMER,
        SELLER,
        ADMINISTRATOR
    }
}
