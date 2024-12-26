package com.phatdo.ecommerce.arena.seller.domain;

import com.phatdo.ecommerce.arena.accountseller.domain.AccountSeller;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@RequiredArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
public class Seller {
    @Id
    @Column(unique = true)
    private final UUID uuid = new UUID(1, UUID.randomUUID().getMostSignificantBits());

    private String name;

    private String shopLogo;

    private String telephone;

    private String country;

    private String city;

    private String province;

    private String district;

    @OneToMany(mappedBy = "seller",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private final Set<AccountSeller> accountSellers = new HashSet<>();

}