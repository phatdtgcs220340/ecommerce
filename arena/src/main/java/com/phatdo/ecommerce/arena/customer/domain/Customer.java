package com.phatdo.ecommerce.arena.customer.domain;

import com.phatdo.ecommerce.arena.account.domain.Account;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
public class Customer {
    @Id
    @GeneratedValue
    @Column(unique = true, nullable = false)
    private UUID uuid;

    private String avatar;

    private String fullName;

    private String telephone;

    private String country;

    @OneToOne
    @JoinColumn(name = "account_uuid")
    private final Account account;

}
