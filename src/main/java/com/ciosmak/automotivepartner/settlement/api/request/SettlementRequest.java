package com.ciosmak.automotivepartner.settlement.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class SettlementRequest
{
    private final LocalDate date;
    private final BigDecimal netProfit;
    private final BigDecimal factor;
    private final BigDecimal tips;
    private final BigDecimal penalties;
    private final BigDecimal finalProfit;
    private final Boolean bugReported;
    private final Long userId;

    @JsonCreator
    public SettlementRequest(LocalDate date, BigDecimal netProfit, BigDecimal factor, BigDecimal tips, BigDecimal penalties, BigDecimal finalProfit, Boolean bugReported, Long userId)
    {
        this.date = date;
        this.netProfit = netProfit;
        this.factor = factor;
        this.tips = tips;
        this.penalties = penalties;
        this.finalProfit = finalProfit;
        this.bugReported = bugReported;
        this.userId = userId;
    }
}
