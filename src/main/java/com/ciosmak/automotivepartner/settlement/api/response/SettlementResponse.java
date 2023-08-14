package com.ciosmak.automotivepartner.settlement.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class SettlementResponse
{
    private final Long id;
    private final LocalDate date;
    private final BigDecimal netProfit;
    private final BigDecimal factor;
    private final BigDecimal tips;
    private final BigDecimal penalties;
    private final BigDecimal finalProfit;
    private final Boolean bugReported;
    private final Long userId;
}
