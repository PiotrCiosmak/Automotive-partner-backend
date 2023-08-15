package com.ciosmak.automotivepartner.settlement.support;

import com.ciosmak.automotivepartner.settlement.api.request.SettlementRequest;
import com.ciosmak.automotivepartner.settlement.api.response.SettlementResponse;
import com.ciosmak.automotivepartner.settlement.domain.Settlement;
import com.ciosmak.automotivepartner.user.repository.UserRepository;
import com.ciosmak.automotivepartner.user.support.UserExceptionSupplier;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Component
public class SettlementMapper
{
    private final UserRepository userRepository;

    public Settlement toSettlement(SettlementRequest settlementRequest, BigDecimal finalProfit, Boolean bugIsReported)
    {
        return new Settlement(adjustDate(settlementRequest.getDate()), settlementRequest.getNetProfit(), settlementRequest.getFactor(), settlementRequest.getTips(), settlementRequest.getPenalties(), finalProfit, bugIsReported, userRepository.findById(settlementRequest.getUserId()).orElseThrow(UserExceptionSupplier.userNotFound(settlementRequest.getUserId())));
    }

    public Settlement toSettlement(Settlement settlement, SettlementRequest settlementRequest, BigDecimal finalProfit, Boolean bugIsReported)
    {
        settlement.setDate(adjustDate(settlementRequest.getDate()));
        settlement.setNetProfit(settlementRequest.getNetProfit());
        settlement.setFactor(settlementRequest.getFactor());
        settlement.setTips(settlementRequest.getTips());
        settlement.setPenalties(settlementRequest.getPenalties());
        settlement.setFinalProfit(finalProfit);
        settlement.setBugReported(bugIsReported);
        settlement.setUser(userRepository.findById(settlementRequest.getUserId()).orElseThrow(UserExceptionSupplier.userNotFound(settlementRequest.getUserId())));
        return settlement;
    }

    private LocalDate adjustDate(LocalDate date)
    {
        return date.withDayOfMonth(1);
    }

    public SettlementResponse toSettlementResponse(Settlement settlement)
    {
        return new SettlementResponse(settlement.getId(), settlement.getDate(), settlement.getNetProfit(), settlement.getFactor(), settlement.getTips(), settlement.getPenalties(), settlement.getFinalProfit(), settlement.getBugReported(), settlement.getUser().getId());
    }
}
