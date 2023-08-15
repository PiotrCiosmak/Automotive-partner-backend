package com.ciosmak.automotivepartner.settlement.repository;

import com.ciosmak.automotivepartner.settlement.domain.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SettlementRepository extends JpaRepository<Settlement, Long>
{
    @Query("SELECT s FROM Settlement s WHERE s.isBugReported = true")
    List<Settlement> findAllWithBugReportedTrue();

    Optional<Settlement> findByUserIdAndDate(Long userId, LocalDate date);
}
