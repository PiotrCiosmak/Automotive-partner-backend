package com.ciosmak.automotivepartner.statistic;

import com.ciosmak.automotivepartner.entity.AbstractEntity;
import com.ciosmak.automotivepartner.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

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
    @Column(name = "month_and_year", columnDefinition = "DATE", nullable = false)
    private Date monthAndYear;

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
    @JoinColumn(name = "user_id")
    private User user;
}