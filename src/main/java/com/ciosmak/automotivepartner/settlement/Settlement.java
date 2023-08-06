package com.ciosmak.automotivepartner.settlement;

import com.ciosmak.automotivepartner.entity.AbstractEntity;
import com.ciosmak.automotivepartner.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

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
    @Column(name = "month", nullable = false)
    private Integer month;

    @ToString.Include
    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "net_profit", scale = 2, nullable = false)
    private BigDecimal netProfit;

    @Column(name = "factor", scale = 2, nullable = false)
    private BigDecimal factor;

    @Column(name = "tips", scale = 2, nullable = false)
    private BigDecimal tips;

    @Column(name = "penalties", scale = 2, nullable = false)
    private BigDecimal penalties;

    @ToString.Include
    @Column(name = "final_profit", scale = 2, nullable = false)
    private BigDecimal finalProfit;

    @Column(name = "bug_reported", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean bugReported;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private User user;
}
