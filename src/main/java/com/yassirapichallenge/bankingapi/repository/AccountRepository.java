package com.yassirapichallenge.bankingapi.repository;
import com.yassirapichallenge.bankingapi.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AccountRepository extends JpaRepository<Account, Long> {}