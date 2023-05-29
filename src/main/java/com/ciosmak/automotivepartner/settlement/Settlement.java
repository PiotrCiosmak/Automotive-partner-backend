package com.ciosmak.automotivepartner.settlement;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "settlements")
public class Settlement
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date monthAndYear;

    private Double netProfit;

    private Double factor;

    private Double tips;

    private Double penalties;

    private Double finalProfit;

    private Boolean bugReported;//DEFAULT FALSE
}