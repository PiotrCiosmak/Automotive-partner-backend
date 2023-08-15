package com.ciosmak.automotivepartner.settlement.service;

import com.ciosmak.automotivepartner.settlement.api.request.SettlementRequest;
import com.ciosmak.automotivepartner.settlement.api.response.SettlementResponse;
import com.ciosmak.automotivepartner.settlement.domain.Settlement;
import com.ciosmak.automotivepartner.settlement.repository.SettlementRepository;
import com.ciosmak.automotivepartner.settlement.support.SettlementExceptionSupplier;
import com.ciosmak.automotivepartner.settlement.support.SettlementMapper;
import com.ciosmak.automotivepartner.user.domain.User;
import com.ciosmak.automotivepartner.user.repository.UserRepository;
import com.ciosmak.automotivepartner.user.support.UserExceptionSupplier;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class SettlementService
{
    private final SettlementRepository settlementRepository;
    private final SettlementMapper settlementMapper;
    private final UserRepository userRepository;

    public SettlementResponse getInfo(Long userId, LocalDate date)
    {
        User user = userRepository.findById(userId).orElseThrow(UserExceptionSupplier.userNotFound(userId));

        LocalDate adjustedDate = adjustDate(date);
        Settlement settlement = getExistingSettlement(user, adjustedDate);

        return settlementMapper.toSettlementResponse(settlement);
    }

    @Transactional
    public SettlementResponse reportBug(Long id)
    {
        Settlement settlement = settlementRepository.findById(id).orElseThrow(SettlementExceptionSupplier.settlementNotFound(id));

        boolean bugIsReported = settlement.getIsBugReported();
        if (bugIsReported)
        {
            throw SettlementExceptionSupplier.bugAlreadyReported().get();
        }

        settlement.setIsBugReported(Boolean.TRUE);

        return settlementMapper.toSettlementResponse(settlement);
    }

    public Boolean isBugReported(Long id)
    {
        Settlement settlement = settlementRepository.findById(id).orElseThrow(SettlementExceptionSupplier.settlementNotFound(id));

        return settlement.getIsBugReported();
    }

    @Transactional
    public SettlementResponse complete(SettlementRequest settlementRequest)
    {
        Long userId = settlementRequest.getUserId();
        User user = userRepository.findById(userId).orElseThrow(UserExceptionSupplier.userNotFound(userId));

        LocalDate adjustedDate = adjustDate(settlementRequest.getDate());
        ensureSettlementNotCompleted(user, adjustedDate);

        checkIfSettlementDataAreCorrect(settlementRequest);

        BigDecimal finalProfit = calculateFinalProfit(settlementRequest.getNetProfit(), settlementRequest.getFactor(), settlementRequest.getTips(), settlementRequest.getPenalties());
        Settlement settlement = settlementRepository.save(settlementMapper.toSettlement(settlementRequest, finalProfit, false));

        return settlementMapper.toSettlementResponse(settlement);
    }

    private void ensureSettlementNotCompleted(User user, LocalDate date)
    {
        Optional<Settlement> settlementCandidate = settlementRepository.findByUserIdAndDate(user.getId(), date);
        if (settlementCandidate.isPresent())
        {
            throw SettlementExceptionSupplier.settlementAlreadyCompleted(user.getFirstName(), user.getLastName(), date.getMonthValue(), date.getYear()).get();
        }
    }

    @Transactional
    public SettlementResponse update(SettlementRequest settlementRequest)
    {
        Long userId = settlementRequest.getUserId();
        User user = userRepository.findById(userId).orElseThrow(UserExceptionSupplier.userNotFound(userId));

        LocalDate adjustedDate = adjustDate(settlementRequest.getDate());
        Settlement settlement = getExistingSettlement(user, adjustedDate);

        checkIfSettlementDataAreCorrect(settlementRequest);

        updateSettlementFields(settlement, settlementRequest);

        return settlementMapper.toSettlementResponse(settlement);
    }

    private Settlement getExistingSettlement(User user, LocalDate date)
    {
        return settlementRepository.findByUserIdAndDate(user.getId(), date).orElseThrow(SettlementExceptionSupplier.settlementIncomplete(user.getFirstName(), user.getLastName(), date.getMonthValue(), date.getYear()));
    }

    private LocalDate adjustDate(LocalDate date)
    {
        return date.withDayOfMonth(1);
    }

    private void checkIfSettlementDataAreCorrect(SettlementRequest settlementRequest)
    {
        BigDecimal netProfit = settlementRequest.getNetProfit();
        checkIfNetProfitIsCorrect(netProfit);

        BigDecimal factor = settlementRequest.getFactor();
        checkIfFactorIsCorrect(factor);

        BigDecimal tips = settlementRequest.getTips();
        checkIfTipsAreCorrect(tips);

        BigDecimal penalties = settlementRequest.getPenalties();
        checkIfPenaltiesAreCorrect(penalties);
    }

    private void checkIfNetProfitIsCorrect(BigDecimal netProfit)
    {
        if (isEmpty(netProfit))
        {
            throw SettlementExceptionSupplier.emptyNetAmount().get();
        }

        if (isLessThenZero(netProfit))
        {
            throw SettlementExceptionSupplier.incorrectNetAmount().get();
        }
    }

    private void checkIfFactorIsCorrect(BigDecimal factor)
    {
        if (isLessThenZero(factor))
        {
            throw SettlementExceptionSupplier.incorrectOptionalFactor().get();
        }
    }

    private void checkIfTipsAreCorrect(BigDecimal tips)
    {
        if (isEmpty(tips))
        {
            throw SettlementExceptionSupplier.emptyTipAmount().get();
        }

        if (isLessThenZero(tips))
        {
            throw SettlementExceptionSupplier.incorrectTipAmount().get();
        }
    }

    private void checkIfPenaltiesAreCorrect(BigDecimal penalties)
    {
        if (isLessThenZero(penalties))
        {
            throw SettlementExceptionSupplier.incorrectOptionalPenaltyAmount().get();
        }
    }

    private boolean isEmpty(BigDecimal amount)
    {
        return amount == null;
    }

    private boolean isLessThenZero(BigDecimal amount)
    {
        return amount.compareTo(BigDecimal.ZERO) < 0;
    }

    private void updateSettlementFields(Settlement settlement, SettlementRequest settlementRequest)
    {
        BigDecimal netProfit = settlementRequest.getNetProfit();
        BigDecimal factor = settlementRequest.getFactor();
        BigDecimal tips = settlementRequest.getTips();
        BigDecimal penalties = settlementRequest.getPenalties();
        BigDecimal finalProfit = calculateFinalProfit(netProfit, factor, tips, penalties);

        settlement.setNetProfit(netProfit);
        settlement.setFactor(factor);
        settlement.setTips(tips);
        settlement.setPenalties(penalties);
        settlement.setFinalProfit(finalProfit);
        settlement.setIsBugReported(Boolean.FALSE);
    }

    private BigDecimal calculateFinalProfit(BigDecimal netProfit, BigDecimal factor, BigDecimal tips, BigDecimal penalties)
    {
        return netProfit.multiply(factor).add(tips).subtract(penalties);
    }

    public List<SettlementResponse> findAllWithReportedBug()
    {
        return settlementRepository.findAllWithBugReportedTrue().stream().map(settlementMapper::toSettlementResponse).collect(Collectors.toList());
    }
}
