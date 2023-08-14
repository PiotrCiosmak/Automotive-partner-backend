package com.ciosmak.automotivepartner.settlement.service;

import com.ciosmak.automotivepartner.settlement.api.request.SettlementRequest;
import com.ciosmak.automotivepartner.settlement.api.response.SettlementResponse;
import com.ciosmak.automotivepartner.settlement.repository.SettlementRepository;
import com.ciosmak.automotivepartner.settlement.support.SettlementMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class SettlementService
{
    private final SettlementRepository settlementRepository;
    private final SettlementMapper settlementMapper;

    public SettlementResponse getInfo(Long userId, LocalDate date)
    {
        return null;
    }

    @Transactional
    public SettlementResponse reportBug(Long id)
    {
        return null;
    }

    public Boolean isBugReported(Long id)
    {
        return null;
    }

    @Transactional
    public SettlementResponse complete(Long userId, SettlementRequest settlementRequest)
    {
        return null;
    }

    @Transactional
    public SettlementResponse update(Long userId, SettlementRequest settlementRequest)
    {
        return null;
    }

    public List<SettlementResponse> findAllWithReportedBug()
    {
        return null;
    }
}
