package com.phatdo.ecommerce.arena.seller.repository;

import com.phatdo.ecommerce.arena.seller.domain.AccountSeller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface AccountSellerRepository extends JpaRepository<AccountSeller, UUID> {
    @Query(value = "SELECT * FROM account_seller " +
                    "WHERE account_uuid = :accountUuid AND seller_uuid = :sellerUuid", nativeQuery = true
    )
    Optional<AccountSeller> findByAccountUuidAndSellerUuid(
            @Param("accountUuid") UUID accountUuid,
            @Param("sellerUuid") UUID sellerUuid
    );

    Page<AccountSeller> findByAccount_Uuid(UUID accountUuid, Pageable pageable);
}
