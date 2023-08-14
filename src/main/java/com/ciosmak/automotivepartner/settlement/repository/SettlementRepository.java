package com.ciosmak.automotivepartner.settlement.repository;

import com.ciosmak.automotivepartner.settlement.domain.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettlementRepository extends JpaRepository<Settlement, Long>
{
}
