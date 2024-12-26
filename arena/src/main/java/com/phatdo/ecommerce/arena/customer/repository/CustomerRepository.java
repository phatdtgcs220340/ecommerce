package com.phatdo.ecommerce.arena.customer.repository;

import com.phatdo.ecommerce.arena.customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
