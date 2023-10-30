package com.ciosmak.automotivepartner.statistic.domain;

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
@Table(name = "statistics")
public class Statistics extends AbstractEntity
{
    @ToString.Include
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ToString.Include
    @Column(name = "mileage", nullable = false)
    private Integer mileage;

    @ToString.Include
    @Column(name = "lpg", scale = 2, nullable = false)
    private BigDecimal lpg;

    @ToString.Include
    @Column(name = "petrol", scale = 2, nullable = false)
    private BigDecimal petrol;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
