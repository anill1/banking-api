package com.yassirapichallenge.bankingapi.repository;
import com.yassirapichallenge.bankingapi.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface TransferRepository extends JpaRepository<Transfer, Long> {
    Optional<List<Transfer>> findBySourceAccountIdOrTargetAccountId(Long sourceAccountId, Long targetAccountId);
}