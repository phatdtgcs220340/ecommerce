package com.phatdo.ecommerce.arena.account.domain;

import com.phatdo.ecommerce.arena.accountseller.domain.AccountSeller;
import com.phatdo.ecommerce.arena.customer.domain.Customer;
import com.phatdo.ecommerce.arena.seller.domain.Seller;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.*;

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
        SELLER
    }
}
