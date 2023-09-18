package com.ciosmak.automotivepartner.settlement.domain;

import com.ciosmak.automotivepartner.shared.entity.AbstractEntity;
import com.ciosmak.automotivepartner.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "settlements")
public class Settlement extends AbstractEntity
{
    @ToString.Include
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "net_profit", scale = 2, nullable = false)
    private BigDecimal netProfit;

    @Column(name = "factor", scale = 2, nullable = false, columnDefinition = "DECIMAL(10, 2) DEFAULT 1.0")
    private BigDecimal factor;

    @Column(name = "tips", scale = 2, nullable = false)
    private BigDecimal tips;

    @Column(name = "penalties", scale = 2, nullable = false, columnDefinition = "DECIMAL(10, 2) DEFAULT 0.0")
    private BigDecimal penalties;

    @ToString.Include
    @Column(name = "final_profit", scale = 2, nullable = false)
    private BigDecimal finalProfit;

    @Column(name = "is_bug_reported", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isBugReported;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private User user;
}
