package com.ciosmak.automotivepartner.statistic;

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
@Table(name = "statistics")
public class Statistic
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date monthAndYear;

    private Integer mileage;

    private Double litersOfGas;

    private Double litersOfGasoline;
}
