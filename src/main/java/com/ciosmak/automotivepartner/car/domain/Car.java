package com.ciosmak.automotivepartner.car.domain;

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
    public Car(String registrationNumber, Integer mileage, Boolean isBlocked)
    {
        this.registrationNumber = registrationNumber;
        this.mileage = mileage;
        this.isBlocked = isBlocked;
    }

    @ToString.Include
    @Column(name = "registration_number", length = 7, nullable = false, unique = true)
    private String registrationNumber;

    @ToString.Include
    @Column(name = "mileage", nullable = false)
    private Integer mileage;

    @Column(name = "is_blocked", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isBlocked;

    @OneToMany(mappedBy = "car", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Shift> shifts = new ArrayList<>();
}
