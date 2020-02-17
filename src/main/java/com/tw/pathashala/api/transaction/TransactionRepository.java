package com.tw.pathashala.api.transaction;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByWalletIdOrderByDateDesc(Long walletId, Pageable pageable);

    @Query(value = "SELECT * FROM transaction t where t.wallet_id = ?1 AND cast(t.date as date) = cast(CURRENT_DATE as date)",nativeQuery = true)
    List<Transaction> findAllByWalletIdAndCurrentDate(long walletId);
}
