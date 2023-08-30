package com.yassirapichallenge.bankingapi.repository;
import com.yassirapichallenge.bankingapi.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CustomerRepository extends JpaRepository<Customer, Long> {}