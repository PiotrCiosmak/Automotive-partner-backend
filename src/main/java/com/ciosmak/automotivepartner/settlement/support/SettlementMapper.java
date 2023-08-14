package com.ciosmak.automotivepartner.settlement.support;

import com.ciosmak.automotivepartner.settlement.api.request.SettlementRequest;
import com.ciosmak.automotivepartner.settlement.api.response.SettlementResponse;
import com.ciosmak.automotivepartner.settlement.domain.Settlement;
import com.ciosmak.automotivepartner.user.repository.UserRepository;
import com.ciosmak.automotivepartner.user.support.UserExceptionSupplier;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class SettlementMapper
{
    private final UserRepository userRepository;

    public Settlement toSettlement(SettlementRequest settlementRequest)
    {
        return new Settlement(settlementRequest.getDate(), settlementRequest.getNetProfit(), settlementRequest.getFactor(), settlementRequest.getTips(), settlementRequest.getPenalties(), settlementRequest.getFinalProfit(), settlementRequest.getBugReported(), userRepository.findById(settlementRequest.getUserId()).orElseThrow(UserExceptionSupplier.userNotFound(settlementRequest.getUserId())));
    }

    public Settlement toSettlement(Settlement settlement, SettlementRequest settlementRequest)
    {
        settlement.setDate(settlementRequest.getDate());
        settlement.setNetProfit(settlementRequest.getNetProfit());
        settlement.setFactor(settlementRequest.getFactor());
        settlement.setTips(settlementRequest.getTips());
        settlement.setPenalties(settlementRequest.getPenalties());
        settlement.setBugReported(settlementRequest.getBugReported());
        settlement.setUser(userRepository.findById(settlementRequest.getUserId()).orElseThrow(UserExceptionSupplier.userNotFound(settlementRequest.getUserId())));
        return settlement;
    }

    public SettlementResponse toSettlementResponse(Settlement settlement)
    {
        return new SettlementResponse(settlement.getId(), settlement.getDate(), settlement.getNetProfit(), settlement.getFactor(), settlement.getTips(), settlement.getPenalties(), settlement.getFinalProfit(), settlement.getBugReported(), settlement.getUser().getId());
    }
}
