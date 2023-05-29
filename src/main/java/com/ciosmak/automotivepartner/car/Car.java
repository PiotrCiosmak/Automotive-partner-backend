package com.ciosmak.automotivepartner.car;

import com.ciosmak.automotivepartner.entity.AbstractEntity;
import com.ciosmak.automotivepartner.shift.Shift;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "cars")
public class Car extends AbstractEntity
{
    @ToString.Include
    @Column(name = "registration_number", length = 7, nullable = false)
    private String registrationNumber;

    @ToString.Include
    @Column(name = "mileage", nullable = false)
    private Integer mileage;

    @Column(name = "blocked", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean blocked;

    @OneToMany(mappedBy = "car", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Shift> shifts = new ArrayList<>();
}
