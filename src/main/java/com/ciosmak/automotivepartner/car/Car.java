package com.ciosmak.automotivepartner.car;

import com.ciosmak.automotivepartner.shift.Shift;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cars")
public class Car
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String registrationNumber;

    private Integer mileage;

    private Boolean blocked;//DEFAULT FALSE

    @OneToMany(mappedBy = "car", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Shift> shifts = new ArrayList<>();
}
