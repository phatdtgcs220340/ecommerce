package com.phatdo.ecommerce.arena.seller.repository;

import com.phatdo.ecommerce.arena.seller.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SellerRepository extends JpaRepository<Seller, UUID> {
}
